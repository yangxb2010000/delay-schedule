package com.tim.delayschedule.server.executor;

import com.tim.delayschedule.core.model.DelayTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 负责执行ScheduleTask
 */
public class ScheduleTaskExecutor {
    private static Logger logger = LoggerFactory.getLogger(ScheduleTaskExecutor.class);
    public static ScheduleTaskExecutor Default = new ScheduleTaskExecutor();

    private Map<String, DelayTaskConsumer> typeToConsumerMap = new HashMap<>();

    public ScheduleTaskExecutor() {
        init();
    }

    public void Execute(DelayTask delayTask) {
        DelayTaskConsumer taskConsumer = typeToConsumerMap.get(delayTask.getType());
        if (taskConsumer == null) {
            logger.warn("no taskConsumer for delayTask id: {}, type: {}. This delayTask will be ignored.", delayTask.getId(), delayTask.getType());
            return;
        }
    }

    private synchronized void init() {
        ServiceLoader<DelayTaskConsumer> consumers = ServiceLoader.load(DelayTaskConsumer.class);

        for (DelayTaskConsumer taskConsumer : consumers) {
            String type = taskConsumer.handledTaskType();

            if (typeToConsumerMap.containsKey(type)) {
                throw new RuntimeException("consumer for type " + type + "has been registered by " + typeToConsumerMap.get(type).getClass());
            }

            typeToConsumerMap.put(type, taskConsumer);
        }
    }
}
