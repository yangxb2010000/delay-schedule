package com.tim.delayschedule.server.executor;

import com.tim.delayschedule.core.model.DelayTask;
import org.junit.Assert;
import org.junit.Test;

public class ScheduleTaskExecutorTest {

    @Test
    public void execute() {
        ScheduleTaskExecutor taskExecutor = new ScheduleTaskExecutor();

        DelayTask delayTaskA = new DelayTask();
        delayTaskA.setType("ScheduleTaskExecutorTest_A");

        DelayTask delayTaskB = new DelayTask();
        delayTaskB.setType("ScheduleTaskExecutorTest_B");

        taskExecutor.Execute(delayTaskA);
        taskExecutor.Execute(delayTaskB);

        Assert.assertEquals(1, delayTaskA.getExecutedCount());
        Assert.assertEquals(2, delayTaskB.getExecutedCount());
    }

    public static class DelayTaskConsumerA implements DelayTaskConsumer {

        @Override
        public Boolean Execute(DelayTask delayTask) {
            delayTask.setExecutedCount(1);
            return true;
        }

        @Override
        public String handledTaskType() {
            return "ScheduleTaskExecutorTest_A";
        }
    }

    public static class DelayTaskConsumerB implements DelayTaskConsumer {

        @Override
        public Boolean Execute(DelayTask delayTask) {
            delayTask.setExecutedCount(2);
            return true;
        }

        @Override
        public String handledTaskType() {
            return "ScheduleTaskExecutorTest_B";
        }
    }
}