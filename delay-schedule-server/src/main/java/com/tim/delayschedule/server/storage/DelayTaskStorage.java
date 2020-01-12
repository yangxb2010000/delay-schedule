package com.tim.delayschedule.server.storage;

import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.core.sharding.SlotRange;

import java.util.List;

/**
 * DelayTask 存储
 */
public interface DelayTaskStorage {
    /**
     * 添加DelayTask
     *
     * @param delayTask
     */
    void addTask(DelayTask delayTask);

    /**
     * 批量添加DelayTask
     *
     * @param delayTask
     */
    void addAllTask(List<DelayTask> delayTask);

    /**
     * 根据指定SlotRange对应的还未执行的
     *
     * @param slotRange
     * @return
     */
    List<DelayTask> loadUnExecutedTask(SlotRange slotRange);
}
