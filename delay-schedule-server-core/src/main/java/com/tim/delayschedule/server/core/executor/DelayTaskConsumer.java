package com.tim.delayschedule.server.core.executor;

import com.tim.delayschedule.server.core.model.ScheduleEntry;

/**
 * 消费DelayTask
 * @author xiaobing
 */
public interface DelayTaskConsumer {
    /**
     * 执行DelayTask的具体实现
     * @param scheduleEntry
     * @return
     */
    Boolean Execute(ScheduleEntry scheduleEntry);

    /**
     * 获取该Consumer负责的TaskType
     * @return
     */
    String handledTaskType();
}
