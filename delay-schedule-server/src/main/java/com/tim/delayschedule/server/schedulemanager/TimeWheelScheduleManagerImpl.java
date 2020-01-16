package com.tim.delayschedule.server.schedulemanager;

import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.core.sharding.SlotRange;
import com.tim.delayschedule.core.sharding.SlotSharding;
import com.tim.delayschedule.core.sharding.ZookeeperSlotSharding;
import com.tim.delayschedule.server.executor.ScheduleTaskExecutor;
import com.tim.delayschedule.server.model.SimpleDelayTask;
import com.tim.delayschedule.server.storage.DelayTaskStorage;
import com.tim.delayschedule.server.storage.JdbcDelayTaskStorage;
import com.tim.delayschedule.server.timer.Timer;
import com.tim.delayschedule.server.timer.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
     *  默认slot的总数
     */
    private static final int DEFAULT_SLOT_COUNT = 16384;

    private Timer timer;
    private ScheduleTaskExecutor taskExecutor;
    private SlotSharding slotSharding;
    private DelayTaskStorage delayTaskStorage;

    private volatile int slotChangeVersion = 0;
    private volatile SlotRange slotRange;

    /**
     * 当slot变化之后，负责从DB中加载DelayTask的线程池
     */
    private ExecutorService loadFromDBThreadPool;

    public TimeWheelScheduleManagerImpl() {
        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                ScheduleTaskExecutor.Default,
                new ZookeeperSlotSharding(),
                new JdbcDelayTaskStorage());
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
    public void push(DelayTask task) {
        if (task.getId() == null || task.getId().length() == 0) {
            throw new RuntimeException("task's id cannot be null or empty.");
        }

        SimpleDelayTask simpleDelayTask = toSimple(task);
        if (!shouldHandle(simpleDelayTask)) {
            throw new RuntimeException("the schedule manager don't handle this task, taskId: " + task.getId());
        }

        //先进行持久化，如果持久化成功才放入内存的timer中，如果写入失败就直接返回失败
        this.delayTaskStorage.addTask(task);

        pushTaskToTimer(simpleDelayTask);
    }

    private void pushTaskToTimer(SimpleDelayTask simpleTask) {
        timer.addTask(new TimerTask(simpleTask.getScheduleTime(), () -> {
            // 由于当前实例覆盖的slot可能会变化，所以执行前需要再次检测一下
            if (shouldHandle(simpleTask)) {
                DelayTask delayTask = this.delayTaskStorage.getTask(simpleTask.getId());
                taskExecutor.Execute(delayTask);
            }
        }));
    }

    /**
     * 重新设置SlotRange
     *
     * @param newSlotRange
     */
    public synchronized void setSlotRange(SlotRange newSlotRange) {
        List<SlotRange> loadMoreSlotRangeList = new ArrayList<>();
        if (this.slotRange == null
                || newSlotRange.getMinSlotId() > this.slotRange.getMaxSlotId()
                || newSlotRange.getMaxSlotId() < this.slotRange.getMinSlotId()) {
            //此时newSlotRange与旧的SlotRange完全没有重叠
            loadMoreSlotRangeList.add(newSlotRange);
        } else {
            //此时是有重叠的场景
            if (newSlotRange.getMinSlotId() < this.slotRange.getMinSlotId()) {
                loadMoreSlotRangeList.add(new SlotRange(newSlotRange.getMinSlotId(), this.slotRange.getMinSlotId()));
            }

            if (newSlotRange.getMaxSlotId() > this.slotRange.getMaxSlotId()) {
                loadMoreSlotRangeList.add(new SlotRange(this.slotRange.getMaxSlotId(), newSlotRange.getMinSlotId()));
            }
        }

        if (loadMoreSlotRangeList.size() == 0) {
            //说明旧的SlotRange完全覆盖新的SlotRange，无须从DB中加载
            this.slotRange = newSlotRange;
            return;
        }

        startLoadDB(loadMoreSlotRangeList, this.slotChangeVersion++);
    }

    private void startLoadDB(List<SlotRange> slotRanges, final int slotVersion) {
        loadFromDBThreadPool.submit(() -> {
            loadFromDB(slotRanges, slotVersion);
        });
    }

    private void loadFromDB(List<SlotRange> slotRanges, int slotVersion) {
        // 往后推迟一秒，尽可能减少服务器时间不同步导致的数据丢失
        long endTime = System.currentTimeMillis() + 1000;
        for (SlotRange slotRange : slotRanges) {
            //如果在从DB中加载DelayTask的时候SlotRange又重新变化了，就退出当前线程
            if (this.slotChangeVersion != slotVersion) {
                logger.debug("found new slotChangeVersion, and current loadingFromDB operation will exit.");
                break;
            }

            long cursor = 0;
            while (this.slotChangeVersion == slotVersion) {
                try {
                    DelayTaskStorage.LoadUnExecutedTaskResult loadResult = this.delayTaskStorage.loadUnExecutedTask(slotRange, cursor, endTime);
                    // 加载完毕就推出当前循环
                    if (loadResult == null || loadResult.getTaskList() == null || loadResult.getTaskList().size() == 0) {
                        break;
                    }

                    for (SimpleDelayTask delayTask : loadResult.getTaskList()) {
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
    }

    private boolean shouldHandle(SimpleDelayTask task) {
        if (task.getSlotId() < 0) {
            task.setSlotId(task.getId().hashCode() & (DEFAULT_SLOT_COUNT));
        }

        return this.slotSharding.shouldHandle(task.getSlotId());
    }

    private SimpleDelayTask toSimple(DelayTask delayTask) {
        SimpleDelayTask sdt = new SimpleDelayTask();
        sdt.setId(delayTask.getId());
        sdt.setSlotId(delayTask.getSlotId());
        sdt.setScheduleTime(delayTask.getScheduleTime());

        return sdt;
    }
}
