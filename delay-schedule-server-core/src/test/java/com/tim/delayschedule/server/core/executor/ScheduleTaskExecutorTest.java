package com.tim.delayschedule.server.core.executor;

import com.tim.delayschedule.server.core.model.ScheduleEntry;
import org.junit.Assert;
import org.junit.Test;

public class ScheduleTaskExecutorTest {

    @Test
    public void execute() {
        ScheduleTaskExecutor taskExecutor = new ScheduleTaskExecutor();

        ScheduleEntry scheduleEntryA = new ScheduleEntry();
        scheduleEntryA.setType("ScheduleTaskExecutorTest_A");

        ScheduleEntry scheduleEntryB = new ScheduleEntry();
        scheduleEntryB.setType("ScheduleTaskExecutorTest_B");

        taskExecutor.Execute(scheduleEntryA);
        taskExecutor.Execute(scheduleEntryB);

        Assert.assertEquals(1, scheduleEntryA.getExecutedCount());
        Assert.assertEquals(2, scheduleEntryB.getExecutedCount());
    }

    public static class DelayTaskConsumerA implements DelayTaskConsumer {

        @Override
        public Boolean Execute(ScheduleEntry scheduleEntry) {
            scheduleEntry.setExecutedCount(1);
            return true;
        }

        @Override
        public String handledTaskType() {
            return "ScheduleTaskExecutorTest_A";
        }
    }

    public static class DelayTaskConsumerB implements DelayTaskConsumer {

        @Override
        public Boolean Execute(ScheduleEntry scheduleEntry) {
            scheduleEntry.setExecutedCount(2);
            return true;
        }

        @Override
        public String handledTaskType() {
            return "ScheduleTaskExecutorTest_B";
        }
    }
}