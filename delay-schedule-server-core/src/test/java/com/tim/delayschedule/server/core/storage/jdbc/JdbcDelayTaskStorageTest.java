package com.tim.delayschedule.server.core.storage.jdbc;

import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.constant.TaskType;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.storage.DelayTaskStorage;
import com.tim.delayschedule.utils.DataSourceUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Ignore
public class JdbcDelayTaskStorageTest {

    private DelayTaskStorage delayTaskStorage = new JdbcDelayTaskStorage(DataSourceUtils.initDataSource());

    @Test
    public void addTask() {
    }

    @Test
    public void addAllTask() {
    }

    @Test
    public void getTask() {
    }

    @Test
    public void markTaskExecuted() {
    }

    @Test
    public void markTaskExecuted1() {
    }

    @Test
    public void loadUnExecutedTask() {

        List<Integer> slotIds = new ArrayList<>();
        long schedultTime = 9999999999999L;

        long cursor = 0;

        slotIds.add(1);
        slotIds.add(2);

        DelayTaskStorage.LoadUnExecutedTaskResult result = delayTaskStorage.loadUnExecutedTask(slotIds,cursor,schedultTime);

        cursor = result.getCursor();

        //分页查询后，插入一条数据
        ScheduleEntry delayTask = new ScheduleEntry();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
        String currentTime = format.format(new Date());
        String uuid = UUID.randomUUID().toString();

        delayTask.setId(uuid);
        delayTask.setSlotId(1);
        delayTask.setStatus(TaskStatus.NEW);
        delayTask.setExecutedCount(2);
        delayTask.setTtr(3);
        delayTask.setPublishTime(System.currentTimeMillis());
        delayTask.setNextScheduleTime(System.currentTimeMillis());
        delayTask.setCreateTime(currentTime);
        delayTask.setPayload("test data");
        delayTask.setUpdateTime(currentTime);
        delayTask.setType(TaskType.DELAY_TASK);

        //插入成功数据
        delayTaskStorage.addTask(delayTask);

        //再次查询，看是否能查到新插入的数据
        result = delayTaskStorage.loadUnExecutedTask(slotIds,result.getCursor(),schedultTime);
        cursor = result.getCursor();

        //判断查出的数据总量是否是1
        Assert.assertEquals(1, result.getTaskList().size());

        //判断查出的数据id是否是最新插入的
        Assert.assertEquals(uuid, result.getTaskList().get(0).getId());

        //再次查询，查询不到数据，cursor不发生变化
        result = delayTaskStorage.loadUnExecutedTask(slotIds,result.getCursor(),schedultTime);

        Assert.assertEquals(cursor, result.getCursor());

        //标记刚插入的数据已完成，防止数据污染
        delayTaskStorage.markTaskExecuted(uuid);

    }
}