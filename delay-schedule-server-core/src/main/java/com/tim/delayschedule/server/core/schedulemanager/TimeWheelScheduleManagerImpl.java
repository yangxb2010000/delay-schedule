package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.storage.DelayTaskStorage;
import com.tim.delayschedule.server.core.storage.JdbcDelayTaskStorage;
import com.tim.delayschedule.server.core.schedulemanager.timer.Timer;
import com.tim.delayschedule.server.core.schedulemanager.timer.TimerTask;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import com.tim.delayschedule.server.core.slotsharding.ZookeeperSlotSharding;
import com.tim.delayschedule.server.core.executor.ScheduleTaskExecutor;
import com.tim.delayschedule.server.core.model.SimpleScheduleEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaobing
 * @date 2020/1/8
 * 基于时间轮实现的ScheduleManager
 */
public class TimeWheelScheduleManagerImpl implements ScheduleManager {
    private static Logger logger = LoggerFactory.getLogger(TimeWheelScheduleManagerImpl.class);

    private static final int DEFAULT_TIMER_TICK = 1000;
    private static final int DEFAULT_TIMER_WHEEL_SIZE = 100;
    /**
     * 默认slot的总数
     */
    private static final int DEFAULT_SLOT_COUNT = 16384;

    private Timer timer;
    private ScheduleTaskExecutor taskExecutor;
    private SlotSharding slotSharding;
    private DelayTaskStorage delayTaskStorage;

    private volatile int slotChangeVersion = 0;
    private volatile Set<Integer> slotSet;

    /**
     * 当slot变化之后，负责从DB中加载DelayTask的线程池
     */
    private ExecutorService loadFromDBThreadPool;

    //TODO 不应该new
//    public TimeWheelScheduleManagerImpl() {
//        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
//                ScheduleTaskExecutor.Default,
//                new ZookeeperSlotSharding(),
//                new JdbcDelayTaskStorage());
//    }

    public TimeWheelScheduleManagerImpl(DataSource dataSource) {
        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                ScheduleTaskExecutor.Default,
                new ZookeeperSlotSharding(),
                new JdbcDelayTaskStorage(dataSource));
    }

    public TimeWheelScheduleManagerImpl(Timer timer,
                                        ScheduleTaskExecutor taskExecutor,
                                        SlotSharding slotSharding,
                                        DelayTaskStorage delayTaskStorage) {
        this.timer = timer;
        this.taskExecutor = taskExecutor;
        this.slotSharding = slotSharding;
        this.delayTaskStorage = delayTaskStorage;

        loadFromDBThreadPool = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("timewheel-schedulemanager-loadfromdb-thread");
            t.setDaemon(true);
            return t;
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void push(ScheduleEntry task) {
        if (task.getId() == null || task.getId().length() == 0) {
            throw new RuntimeException("task's id cannot be null or empty.");
        }

        SimpleScheduleEntry simpleScheduleEntry = toSimple(task);
        if (!shouldHandle(simpleScheduleEntry)) {
            throw new RuntimeException("the schedule manager don't handle this task, taskId: " + task.getId());
        }

        //先进行持久化，如果持久化成功才放入内存的timer中，如果写入失败就直接返回失败
        this.delayTaskStorage.addTask(task);

        pushTaskToTimer(simpleScheduleEntry);
    }

    private void pushTaskToTimer(SimpleScheduleEntry simpleTask) {
        timer.addTask(new TimerTask(simpleTask.getScheduleTime(), () -> {
            // 由于当前实例覆盖的slot可能会变化，所以执行前需要再次检测一下
            if (shouldHandle(simpleTask)) {
                ScheduleEntry scheduleEntry = this.delayTaskStorage.getTask(simpleTask.getId());
                taskExecutor.Execute(scheduleEntry);
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

        this.slotSet = newSlotSet;

        if (loadMoreSlotList.size() == 0) {
            //说明旧的SlotRange完全覆盖新的SlotRange，无须从DB中加载
            return;
        }

        startLoadDB(loadMoreSlotList, this.slotChangeVersion++);
    }

    private void startLoadDB(List<Integer> slotIdList, final int slotVersion) {
        loadFromDBThreadPool.submit(() -> {
            loadFromDB(slotIdList, slotVersion);
        });
    }

    private void loadFromDB(List<Integer> slotIdList, int slotVersion) {
        // 往后推迟一秒，尽可能减少服务器时间不同步导致的数据丢失
        long endTime = System.currentTimeMillis() + 1000;
        long cursor = 0;
        //如果在从DB中加载DelayTask的时候SlotRange又重新变化了，就退出当前线程
        while (this.slotChangeVersion == slotVersion) {
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
                    logger.warn("interrupt happened when sleeping for next load from db.");
                }
            }
        }
    }

    private boolean shouldHandle(SimpleScheduleEntry task) {
        if (task.getSlotId() < 0) {
            task.setSlotId(task.getId().hashCode() & (DEFAULT_SLOT_COUNT));
        }

        return this.slotSharding.shouldHandle(task.getSlotId());
    }

    private SimpleScheduleEntry toSimple(ScheduleEntry scheduleEntry) {
        SimpleScheduleEntry sdt = new SimpleScheduleEntry();
        sdt.setId(scheduleEntry.getId());
        sdt.setSlotId(scheduleEntry.getSlotId());
        sdt.setScheduleTime(scheduleEntry.getScheduleTime());

        return sdt;
    }
}
