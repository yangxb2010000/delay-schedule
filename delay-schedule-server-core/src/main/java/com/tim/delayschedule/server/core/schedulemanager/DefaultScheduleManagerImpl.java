package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.executor.ScheduleTaskExecutor;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.model.SimpleScheduleEntry;
import com.tim.delayschedule.server.core.rpc.ScheduleClient;
import com.tim.delayschedule.server.core.schedulemanager.timer.Timer;
import com.tim.delayschedule.server.core.schedulemanager.timer.TimerTask;
import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import com.tim.delayschedule.server.core.storage.DelayTaskStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiaobing
 * @date 2020/1/19
 */
public class DefaultScheduleManagerImpl implements ScheduleManager {
    private InternalScheduleManagerImpl internalImpl;
    private SlotSharding slotSharding;
    private ScheduleClient scheduleClient;

    public DefaultScheduleManagerImpl(
            SlotSharding slotSharding,
            DelayTaskStorage delayTaskStorage,
            ScheduleTaskExecutor taskExecutor,
            ScheduleClient scheduleClient) {
        this.slotSharding = slotSharding;
        this.scheduleClient = scheduleClient;
        internalImpl = new InternalScheduleManagerImpl(taskExecutor, delayTaskStorage);

        this.init();
    }

    public DefaultScheduleManagerImpl(
            Timer timer,
            SlotSharding slotSharding,
            DelayTaskStorage delayTaskStorage,
            ScheduleTaskExecutor taskExecutor,
            ScheduleClient scheduleClient) {
        this.slotSharding = slotSharding;
        this.scheduleClient = scheduleClient;
        internalImpl = new InternalScheduleManagerImpl(timer, taskExecutor, delayTaskStorage);

        this.init();
    }

    @Override
    public void init() {
        this.slotSharding.registerHandledSlotChangeListener((slotIdList) -> {
            this.internalImpl.changeHandledSlot(slotIdList);
        });

        this.slotSharding.registerServer2SlotChangeListener((server2SlotMap) -> {
            //TODO： update schedule client server2SlotMap
        });
    }

    @Override
    public void push(ScheduleEntry task) {
        if (this.internalImpl.push(task) != PushScheduleEntryResult.FAIL_NOTHANDLED) {
            return;
        }

        //如果当前SchedulerServer不负责该处理就尝试调用远程Server
        this.scheduleClient.push(task);
    }

    /**
     * 当前内存中实现的ScheduleManager
     */
    public static class InternalScheduleManagerImpl {
        private Logger logger = LoggerFactory.getLogger(InternalScheduleManagerImpl.class);

        private ReentrantReadWriteLock handledSlotSetLock = new ReentrantReadWriteLock();

        public static final int DEFAULT_TIMER_TICK = 1000;
        public static final int DEFAULT_TIMER_WHEEL_SIZE = 100;

        private Timer timer;
        private ScheduleTaskExecutor taskExecutor;
        private DelayTaskStorage delayTaskStorage;

        private volatile Future loadFromDBFuture = null;
        private volatile Set<Integer> slotSet;

        /**
         * 当slot变化之后，负责从DB中加载DelayTask的线程池
         */
        private ExecutorService loadFromDBThreadPool;

        public InternalScheduleManagerImpl(ScheduleTaskExecutor taskExecutor,
                                           DelayTaskStorage delayTaskStorage) {
            this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                    taskExecutor,
                    delayTaskStorage);
        }

        public InternalScheduleManagerImpl(Timer timer,
                                           ScheduleTaskExecutor taskExecutor,
                                           DelayTaskStorage delayTaskStorage) {
            this.timer = timer;
            this.taskExecutor = taskExecutor;
            this.delayTaskStorage = delayTaskStorage;

            loadFromDBThreadPool = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setName("timewheel-schedulemanager-loadfromdb-thread");
                t.setDaemon(true);
                return t;
            });
        }

        public PushScheduleEntryResult push(ScheduleEntry task) {
            if (task.getId() == null || task.getId().length() == 0) {
                throw new RuntimeException("task's id cannot be null or empty.");
            }

            handledSlotSetLock.readLock().lock();
            try {
                SimpleScheduleEntry simpleScheduleEntry = toSimple(task);
                if (!shouldHandle(simpleScheduleEntry)) {
                    return PushScheduleEntryResult.FAIL_NOTHANDLED;
                }

                //先进行持久化，如果持久化成功才放入内存的timer中，如果写入失败就直接返回失败
                this.delayTaskStorage.addTask(task);

                pushTaskToTimer(simpleScheduleEntry);
            } finally {
                handledSlotSetLock.readLock().unlock();
            }
            return PushScheduleEntryResult.SUCCESS;
        }

        private void pushTaskToTimer(SimpleScheduleEntry simpleTask) {
            timer.addTask(new TimerTask(simpleTask.getScheduleTime(), () -> {
                // 由于当前实例覆盖的slot可能会变化，所以执行前需要再次检测一下
                if (shouldHandle(simpleTask)) {
                    ScheduleEntry scheduleEntry = this.delayTaskStorage.getTask(simpleTask.getId());

                    taskExecutor.Execute(scheduleEntry);

                    this.delayTaskStorage.markTaskExecuted(simpleTask.getId());
                }
            }));
        }

        /**
         * 重新设置SlotRange
         *
         * @param newSlotIdList
         */
        public synchronized void changeHandledSlot(List<Integer> newSlotIdList) {
            Set<Integer> newSlotSet = new HashSet<>();
            List<Integer> loadMoreSlotList = new ArrayList<>();

            for (Integer slotId : newSlotIdList) {
                if (!newSlotSet.contains(slotId)) {
                    newSlotSet.add(slotId);
                }

                if (!this.slotSet.contains(slotId)) {
                    loadMoreSlotList.add(slotId);
                }
            }

            //更新slotSet要加写锁，防止pushTask期间slotSet被修改
            handledSlotSetLock.writeLock().lock();
            this.slotSet = newSlotSet;
            handledSlotSetLock.writeLock().unlock();

            if (loadMoreSlotList.size() == 0) {
                //说明旧的SlotRange完全覆盖新的SlotRange，无须从DB中加载
                return;
            }

            // 等待loadMoreSlotList对应的旧的Server释放slotId

            startLoadDB(loadMoreSlotList);
        }

        private void startLoadDB(List<Integer> slotIdList) {
            if (this.loadFromDBFuture != null) {
                //取消上一次从DB中加载的线程
                this.loadFromDBFuture.cancel(true);
            }

            this.loadFromDBFuture = loadFromDBThreadPool.submit(() -> {
                loadFromDB(slotIdList);
            });
        }

        private boolean shouldHandle(SimpleScheduleEntry task) {
            if (task.getSlotId() < 0) {
                task.setSlotId(task.getId().hashCode() & (SlotSharding.DEFAULT_SLOT_COUNT));
            }

            return this.slotSet.contains(task.getSlotId());
        }

        private void loadFromDB(List<Integer> slotIdList) {
            // 往后推迟一秒，尽可能减少服务器时间不同步导致的数据丢失
            long endTime = System.currentTimeMillis() + 1000;
            long cursor = 0;
            //如果在从DB中加载DelayTask的时候SlotRange又重新变化了，就退出当前线程
            while (!Thread.interrupted()) {
                try {
                    DelayTaskStorage.LoadUnExecutedTaskResult loadResult = this.delayTaskStorage.loadUnExecutedTask(slotIdList, cursor, endTime);
                    // 加载完毕就推出当前循环
                    if (loadResult == null || loadResult.getTaskList() == null || loadResult.getTaskList().size() == 0) {
                        break;
                    }

                    for (SimpleScheduleEntry delayTask : loadResult.getTaskList()) {
                        pushTaskToTimer(delayTask);
                    }

                    cursor = loadResult.getCursor();
                } catch (Exception ex) {
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        //如果在sleep的时候被打断，说明有了新的SlotRange，直接退出循环
                        if (logger.isDebugEnabled()) {
                            logger.warn("interrupt happened when sleeping for next load from db, then will exit this loop");
                        }

                        break;
                    }
                }
            }
        }

        private SimpleScheduleEntry toSimple(ScheduleEntry scheduleEntry) {
            SimpleScheduleEntry sdt = new SimpleScheduleEntry();
            sdt.setId(scheduleEntry.getId());
            sdt.setSlotId(scheduleEntry.getSlotId());
            sdt.setScheduleTime(scheduleEntry.getScheduleTime());

            return sdt;
        }
    }

    /**
     * 通过rpc调用其他SchedulerServer实现转发逻辑
     */
    public static class RemoteScheduleManagerImpl {

    }
}
