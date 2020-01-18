package com.tim.delayschedule.server.rabbitmq.springboot.starter.rabbitmq;

import com.tim.delayschedule.server.core.executor.DelayTaskConsumer;
import com.tim.delayschedule.server.core.model.ScheduleEntry;

/**
 * User: xiaobing
 * Date: 2020/1/18 21:38
 * Description:
 */
public class RabbitMQConsumer implements DelayTaskConsumer {
    @Override
    public Boolean Execute(ScheduleEntry scheduleEntry) {
        return null;
    }

    @Override
    public String handledTaskType() {
        return null;
    }
}