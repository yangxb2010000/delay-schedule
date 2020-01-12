package com.tim.delayschedule.server.schedulemanager;

import com.tim.delayschedule.core.model.DelayTask;

import java.text.MessageFormat;

/**
 * 负责执行ScheduleTask
 */
public class ScheduleTaskExecutor {
    public static ScheduleTaskExecutor Default = new ScheduleTaskExecutor();

    public void Execute(DelayTask delayTask) {
        System.out.println(MessageFormat.format("executing delayTask, id: {0}, delayTime: {1}, executingTime: {2}, difference: {3}",
                delayTask.getId(),
                delayTask.getScheduleTime(),
                System.currentTimeMillis(),
                System.currentTimeMillis() - delayTask.getScheduleTime()));
    }
}
