package com.tim.delayschedule.server.storage.dao.impl;

import com.tim.delayschedule.core.constant.TaskType;
import com.tim.delayschedule.server.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.server.model.Entry;
import com.tim.delayschedule.server.storage.dao.DelayTaskDao;
import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.utils.DataSourceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Ignore
public class DelayTaskDaoImplTest {

    private static DelayTaskDao delayTaskDao = new DelayTaskDaoImpl(DataSourceUtils.initDataSource());

    @Test
    public void select() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
        DelayTask result;

        result = delayTaskDao.select(id);
        Assert.assertNotEquals(null, result);

        id = "925763fa-bbf2-45dd-b415-dfd5ac463701";
        result = delayTaskDao.select(id);
        Assert.assertEquals(null, result);
    }

    @Test
    public void selectBySlotIdWithScheduleTime(){
        List<DelayTask> result = null;

        List<Integer> slotIds = new ArrayList<>();
        long schedultTime = 9999999999999L;

        int pageSize = 3;
        int cursor = 0;

        slotIds.add(1);
        slotIds.add(2);

        result = delayTaskDao.selectBySlotIdWithScheduleTime(slotIds,schedultTime, pageSize, cursor);

        System.out.println(result);

        Assert.assertEquals(3,result.size());

        result = delayTaskDao.selectBySlotIdWithScheduleTime(slotIds,schedultTime, pageSize, cursor + pageSize);

        System.out.println(result);

        Assert.assertEquals(3,result.size());

        result = delayTaskDao.selectBySlotIdWithScheduleTime(slotIds,schedultTime, pageSize, cursor + 2*pageSize);

        System.out.println(result);

        Assert.assertEquals(3,result.size());

        DelayTask delayTask = new DelayTask();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());

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
        delayTask.setType(TaskType.DELAY_TASK);

        //插入成功数据
        delayTaskDao.insert(delayTask);

        result = delayTaskDao.selectBySlotIdWithScheduleTime(slotIds,schedultTime, pageSize, cursor + 3*pageSize);

        System.out.println(result);

        Assert.assertEquals(2,result.size());
    }

    @Test
    public void delete() {
        String id = "4d8f1a67-8fb6-4408-b7ce-8f273ef7286a";
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
        delayTask.setType(TaskType.DELAY_TASK);


        //插入成功数据
        result = delayTaskDao.insert(delayTask);

        Assert.assertEquals(TaskDaoResult.INSERT_SUCCESS, result);

        //插入无效数据
        result = delayTaskDao.insert(null);

        Assert.assertEquals(TaskDaoResult.INSERT_ERROR, result);
    }

    @Test
    public void insertBatch(){
        DelayTask delayTask = new DelayTask();
        DelayTask delayTask2 = new DelayTask();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());
        List<DelayTask> delayTasks = new ArrayList<>();
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
        delayTask.setType(TaskType.DELAY_TASK);

        delayTasks.add(delayTask);

        delayTask2.setId(UUID.randomUUID().toString());
        delayTask2.setSlotId(1);
        delayTask2.setStatus(TaskStatus.DELAY);
        delayTask2.setExecutedCount(2);
        delayTask2.setTtr(3);
        delayTask2.setPublishTime(System.currentTimeMillis());
        delayTask2.setScheduleTime(System.currentTimeMillis());
        delayTask2.setCreateTime(currentTime);
        delayTask2.setPayload("test data");
        delayTask2.setUpdateTime(currentTime);
        delayTask2.setType(TaskType.DELAY_TASK);
        delayTasks.add(delayTask2);

        result = delayTaskDao.insertBatch(delayTasks);

        Assert.assertEquals(TaskDaoResult.INSERT_SUCCESS,result);
    }

    @Test
    public void updateStatusById() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
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

    @Test
    public void updateStatusByIdBatch() {
        String id = "cf9f4a82-1278-4eaf-be75-5dbb3fcb5d0a";
        String id2 = "84de4353-febd-442f-8be1-3e0ad80cbc17";

        TaskStatus status = TaskStatus.DELETED;
        TaskStatus status2 = TaskStatus.RESERVED;

        List<Entry<String, TaskStatus>> taskIdAndStatus = new ArrayList<>();
        taskIdAndStatus.add(new Entry<>(id, status));
        taskIdAndStatus.add(new Entry<>(id2, status2));

        TaskDaoResult result = delayTaskDao.updateStatusByIdBatch(taskIdAndStatus);

        Assert.assertEquals(TaskDaoResult.UPDATE_SUCCESS, result);

        DelayTask delayTask = delayTaskDao.select(id);
        Assert.assertEquals(status, delayTask.getStatus());

        delayTask = delayTaskDao.select(id2);
        Assert.assertEquals(status2, delayTask.getStatus());

    }

}