package com.tim.delayschedule.server.schedulemanager;

import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.core.sharding.SlotSharding;
import com.tim.delayschedule.core.sharding.ZookeeperSlotSharding;
import com.tim.delayschedule.server.executor.ScheduleTaskExecutor;
import com.tim.delayschedule.server.storage.DelayTaskStorage;
import com.tim.delayschedule.server.storage.JdbcDelayTaskStorage;
import com.tim.delayschedule.server.timer.Timer;
import com.tim.delayschedule.server.timer.TimerTask;

/**
 * @author xiaobing
 * @date 2020/1/8
 * 基于时间轮实现的ScheduleManager
 */
public class TimeWheelScheduleManagerImpl implements ScheduleManager {
    private Timer timer;
    private ScheduleTaskExecutor taskExecutor;
    private SlotSharding slotSharding;
    private DelayTaskStorage delayTaskStorage;

    private static final int DEFAULT_TIMER_TICK = 1000;
    private static final int DEFAULT_TIMER_WHEEL_SIZE = 100;
    // 默认slot的总数
    private static final int DEFAULT_SLOT_COUNT = 16384;

    public TimeWheelScheduleManagerImpl() {
        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                ScheduleTaskExecutor.Default,
                new ZookeeperSlotSharding(),
                new JdbcDelayTaskStorage());
    }

    public TimeWheelScheduleManagerImpl(ScheduleTaskExecutor taskExecutor,
                                        SlotSharding slotSharding) {
        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                taskExecutor,
                slotSharding,
                new JdbcDelayTaskStorage());
    }

    public TimeWheelScheduleManagerImpl(SlotSharding slotSharding) {
        this(new Timer(DEFAULT_TIMER_TICK, DEFAULT_TIMER_WHEEL_SIZE),
                ScheduleTaskExecutor.Default,
                slotSharding,
                new JdbcDelayTaskStorage());
    }

    public TimeWheelScheduleManagerImpl(DelayTaskStorage delayTaskStorage) {
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
    }

    public void init() {

    }

    public void push(DelayTask task) {
        if (task.getId() == null || task.getId().length() == 0) {
            throw new RuntimeException("task's id cannot be null or empty.");
        }

        if (!shouldHandle(task)) {
            throw new RuntimeException("the schedule manager don't handle this task, taskId: " + task.getId());
        }

        //先进行持久化，如果持久化成功才放入内存的timer中，如果写入失败就直接返回失败
        this.delayTaskStorage.addTask(task);

        timer.addTask(new TimerTask(task.getScheduleTime(), () -> {
            // 由于当前实例覆盖的slot可能会变化，所以执行前需要再次检测一下
            if (shouldHandle(task)) {
                taskExecutor.Execute(task);
            }
        }));
    }

    private boolean shouldHandle(DelayTask task) {
        if (task.getSlotId() < 0) {
            task.setSlotId(task.getId().hashCode() & (DEFAULT_SLOT_COUNT));
        }

        return this.slotSharding.shouldHandle(task.getSlotId());
    }
}
