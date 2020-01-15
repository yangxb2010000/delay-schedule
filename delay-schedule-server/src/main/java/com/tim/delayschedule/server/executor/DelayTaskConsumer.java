package com.tim.delayschedule.server.executor;

import com.tim.delayschedule.core.model.DelayTask;

/**
 * 消费DelayTask
 * @author xiaobing
 */
public interface DelayTaskConsumer {
    /**
     * 执行DelayTask的具体实现
     * @param delayTask
     * @return
     */
    Boolean Execute(DelayTask delayTask);

    /**
     * 获取该Consumer负责的TaskType
     * @return
     */
    String handledTaskType();
}
