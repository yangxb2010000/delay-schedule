package com.tim.delayschedule.server.core.storage;

import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.model.SimpleScheduleEntry;
import lombok.Data;

import java.util.List;

/**
 * DelayTask 存储
 */
public interface DelayTaskStorage {
    /**
     * 添加DelayTask
     *
     * @param scheduleEntry
     */
    void addTask(ScheduleEntry scheduleEntry);

    /**
     * 批量添加DelayTask
     *
     * @param scheduleEntry
     */
    void addAllTask(List<ScheduleEntry> scheduleEntry);

    /**
     * 获取DelayTask
     *
     * @param id
     */
    ScheduleEntry getTask(String id);

    /**
     * 标记Task已经执行完成
     *
     * @param taskIdList
     */
    void markTaskExecuted(List<String> taskIdList);

    /**
     * 标记Task已经执行完成
     *
     * @param taskId
     */
    void markTaskExecuted(String taskId);

    /**
     * 根据指定SlotRange对应的还未执行的DelayTask
     *
     * @param slotIdList
     * @param cursor     游标，从cursor往后的才加载
     * @param endTime    截至时间，只加载endTime之前的数据
     * @return
     */
    LoadUnExecutedTaskResult loadUnExecutedTask(List<Integer> slotIdList, long cursor, long endTime);

    @Data
    class LoadUnExecutedTaskResult {
        /**
         * 分批加载时的cursor
         */
        private long cursor;

        /**
         * taskList
         */
        private List<SimpleScheduleEntry> taskList;

    }
}
