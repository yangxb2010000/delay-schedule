package com.tim.delayschedule.server.core.constant;

import org.junit.Assert;
import org.junit.Test;


public class TaskStatusTest {

    @Test
    public void assertValue(){

        TaskStatus taskStatusEnum = TaskStatus.NEW;
        Assert.assertEquals(TaskStatus.NEW, taskStatusEnum);

        Assert.assertEquals(1,taskStatusEnum.toValue());

        Assert.assertEquals(TaskStatus.fromvalue(2),TaskStatus.DELETED);
    }

}