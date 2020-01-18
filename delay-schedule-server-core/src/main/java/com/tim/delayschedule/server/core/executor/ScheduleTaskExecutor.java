package com.tim.delayschedule.server.core.executor;

import com.tim.delayschedule.server.core.model.ScheduleEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 负责执行ScheduleTask
 * @author xiaobing
 */
public class ScheduleTaskExecutor {
    private static Logger logger = LoggerFactory.getLogger(ScheduleTaskExecutor.class);
    public static ScheduleTaskExecutor Default = new ScheduleTaskExecutor();

    private Map<String, DelayTaskConsumer> typeToConsumerMap = new HashMap<>();

    public ScheduleTaskExecutor() {
        init();
    }

    public void Execute(ScheduleEntry scheduleEntry) {
        DelayTaskConsumer taskConsumer = typeToConsumerMap.get(scheduleEntry.getType());
        if (taskConsumer == null) {
            logger.warn("no taskConsumer for delayTask id: {}, type: {}. This delayTask will be ignored.", scheduleEntry.getId(), scheduleEntry.getType());
            return;
        }

        taskConsumer.Execute(scheduleEntry);
    }

    private void init() {
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
