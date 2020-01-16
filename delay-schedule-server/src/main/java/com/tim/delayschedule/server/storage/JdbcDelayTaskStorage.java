package com.tim.delayschedule.server.storage;

import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.core.sharding.SlotRange;

import java.util.List;

/**
 * jdbc的方式实现DelayTaskStorage
 */
public class JdbcDelayTaskStorage implements DelayTaskStorage {

    @Override
    public void addTask(DelayTask delayTask) {

    }

    @Override
    public void addAllTask(List<DelayTask> delayTask) {

    }

    @Override
    public DelayTask getTask(String id) {
        return null;
    }

    @Override
    public void markTaskExecuted(List<String> taskIdList) {

    }

    @Override
    public void markTaskExecuted(String taskId) {

    }

    @Override
    public LoadUnExecutedTaskResult loadUnExecutedTask(SlotRange slotRange, long cursor, long endTime) {
        return null;
    }
}
