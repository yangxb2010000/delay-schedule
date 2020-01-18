package com.tim.delayschedule.server.core.storage.dao.impl;

import com.tim.delayschedule.server.core.constant.TaskType;
import com.tim.delayschedule.server.core.constant.TaskDaoResult;
import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.KeyValuePair;
import com.tim.delayschedule.server.core.storage.dao.DelayTaskDao;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.utils.DataSourceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Ignore
public class ScheduleKeyValuePairDaoImplTest {

    private static DelayTaskDao delayTaskDao = new DelayTaskDaoImpl(DataSourceUtils.initDataSource());

    @Test
    public void select() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
        ScheduleEntry result;

        result = delayTaskDao.select(id);
        Assert.assertNotEquals(null, result);

        id = "925763fa-bbf2-45dd-b415-dfd5ac463701";
        result = delayTaskDao.select(id);
        Assert.assertEquals(null, result);
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
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());
        TaskDaoResult result;


        scheduleEntry.setId(UUID.randomUUID().toString());
        scheduleEntry.setSlotId(1);
        scheduleEntry.setStatus(TaskStatus.DELAY);
        scheduleEntry.setExecutedCount(2);
        scheduleEntry.setTtr(3);
        scheduleEntry.setPublishTime(System.currentTimeMillis());
        scheduleEntry.setScheduleTime(System.currentTimeMillis());
        scheduleEntry.setCreateTime(currentTime);
        scheduleEntry.setPayload("test data");
        scheduleEntry.setUpdateTime(currentTime);
        scheduleEntry.setType(TaskType.DELAY_TASK);


        //插入成功数据
        result = delayTaskDao.insert(scheduleEntry);

        Assert.assertEquals(TaskDaoResult.INSERT_SUCCESS, result);

        //插入无效数据
        result = delayTaskDao.insert(null);

        Assert.assertEquals(TaskDaoResult.INSERT_ERROR, result);
    }

    @Test
    public void insertBatch(){
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        ScheduleEntry scheduleEntry2 = new ScheduleEntry();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());
        List<ScheduleEntry> scheduleEntries = new ArrayList<>();
        TaskDaoResult result;


        scheduleEntry.setId(UUID.randomUUID().toString());
        scheduleEntry.setSlotId(1);
        scheduleEntry.setStatus(TaskStatus.DELAY);
        scheduleEntry.setExecutedCount(2);
        scheduleEntry.setTtr(3);
        scheduleEntry.setPublishTime(System.currentTimeMillis());
        scheduleEntry.setScheduleTime(System.currentTimeMillis());
        scheduleEntry.setCreateTime(currentTime);
        scheduleEntry.setPayload("test data");
        scheduleEntry.setUpdateTime(currentTime);
        scheduleEntry.setType(TaskType.DELAY_TASK);

        scheduleEntries.add(scheduleEntry);

        scheduleEntry2.setId(UUID.randomUUID().toString());
        scheduleEntry2.setSlotId(1);
        scheduleEntry2.setStatus(TaskStatus.DELAY);
        scheduleEntry2.setExecutedCount(2);
        scheduleEntry2.setTtr(3);
        scheduleEntry2.setPublishTime(System.currentTimeMillis());
        scheduleEntry2.setScheduleTime(System.currentTimeMillis());
        scheduleEntry2.setCreateTime(currentTime);
        scheduleEntry2.setPayload("test data");
        scheduleEntry2.setUpdateTime(currentTime);
        scheduleEntry2.setType(TaskType.DELAY_TASK);
        scheduleEntries.add(scheduleEntry2);

        result = delayTaskDao.insertBatch(scheduleEntries);

        Assert.assertEquals(TaskDaoResult.INSERT_SUCCESS,result);
    }

    @Test
    public void updateStatusById() {
        String id = "77396317-fd03-4928-baf2-5c6273982343";
        TaskDaoResult result;

        result = delayTaskDao.updateStatusById(id, TaskStatus.READY);
        Assert.assertEquals(TaskDaoResult.UPDATE_SUCCESS, result);


        ScheduleEntry scheduleEntry = null;

        scheduleEntry = delayTaskDao.select(id);
        Assert.assertEquals(TaskStatus.READY, scheduleEntry.getStatus());

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

        List<KeyValuePair<String, TaskStatus>> taskIdAndStatus = new ArrayList<>();
        taskIdAndStatus.add(new KeyValuePair<>(id, status));
        taskIdAndStatus.add(new KeyValuePair<>(id2, status2));

        TaskDaoResult result = delayTaskDao.updateStatusByIdBatch(taskIdAndStatus);

        Assert.assertEquals(TaskDaoResult.UPDATE_SUCCESS, result);

        ScheduleEntry scheduleEntry = delayTaskDao.select(id);
        Assert.assertEquals(status, scheduleEntry.getStatus());

        scheduleEntry = delayTaskDao.select(id2);
        Assert.assertEquals(status2, scheduleEntry.getStatus());


    }
}