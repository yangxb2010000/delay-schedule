package com.tim.delayschedule.core.constant;

import org.junit.Assert;
import org.junit.Test;


public class TaskStatusTest {

    @Test
    public void assertValue(){

        TaskStatus taskStatusEnum = TaskStatus.READY;
        Assert.assertEquals(TaskStatus.READY, taskStatusEnum);

        Assert.assertEquals(1,taskStatusEnum.toValue());

        Assert.assertEquals(TaskStatus.fromvalue(2),TaskStatus.RESERVED);
    }
}