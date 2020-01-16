package com.tim.delayschedule.server.storage.dao.impl;

import com.tim.delayschedule.server.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.server.storage.dao.DelayTaskDao;
import com.tim.delayschedule.core.model.DelayTask;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Ignore
public class DelayTaskDaoImplTest {

    @Test
    public void select() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
        DelayTaskDao delayTaskDao = new DelayTaskDaoImpl();
        DelayTask result;

        result = delayTaskDao.select(id);
        Assert.assertNotEquals(null, result);

        id = "925763fa-bbf2-45dd-b415-dfd5ac463701";
        result = delayTaskDao.select(id);
        Assert.assertEquals(null, result);
    }

    @Test
    public void delete() {
        String id = "f115beed-2063-4c1e-bc3a-bf1ec4dd9e71";
        DelayTaskDao delayTaskDao = new DelayTaskDaoImpl();
        TaskDaoResult result;

        //删除成功
        result = delayTaskDao.delete(id);
        Assert.assertEquals(TaskDaoResult.DELETE_SUCCESS, result);

        //删除失败
        result = delayTaskDao.delete(id);
        Assert.assertEquals(TaskDaoResult.DELETE_ERROR, result);
    }

    @Test
    public void insert() {
        DelayTaskDao delayTaskDao = new DelayTaskDaoImpl();
        DelayTask delayTask = new DelayTask();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());
        TaskDaoResult result;


        delayTask.setId(UUID.randomUUID().toString());
        delayTask.setSlotId(1);
        delayTask.setStatus(TaskStatus.DELAY);
        delayTask.setExecutedCount(2);
        delayTask.setTtr(3);
        delayTask.setPublishTime(System.currentTimeMillis());
        delayTask.setScheduleTime(System.currentTimeMillis());
        delayTask.setCreateTime(currentTime);
        delayTask.setPayload("test data");
        delayTask.setUpdateTime(currentTime);
        delayTask.setType("delay_task");


        //插入成功数据
        result = delayTaskDao.insert(delayTask);

        Assert.assertEquals(TaskDaoResult.INSERT_SUCCESS, result);

        //插入无效数据
        result = delayTaskDao.insert(null);

        Assert.assertEquals(TaskDaoResult.INSERT_ERROR, result);
    }

    @Test
    public void updateStatusById() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
        DelayTaskDao delayTaskDao = new DelayTaskDaoImpl();
        TaskDaoResult result;

        result = delayTaskDao.updateStatusById(id, TaskStatus.READY);
        Assert.assertEquals(TaskDaoResult.UPDATE_SUCCESS, result);


        DelayTask delayTask = null;

        delayTask = delayTaskDao.select(id);
        Assert.assertEquals(TaskStatus.READY, delayTask.getStatus());

        id = "925763fa-bbf2-45dd-b415-dfd5ac463701";
        result = delayTaskDao.updateStatusById(id, TaskStatus.DELETED);

        Assert.assertEquals(TaskDaoResult.UPDATE_ERROR, result);

    }
}