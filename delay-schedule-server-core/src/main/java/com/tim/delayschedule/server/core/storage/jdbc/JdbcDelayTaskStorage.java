package com.tim.delayschedule.server.core.storage.jdbc;

import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.constant.TaskDaoResult;
import com.tim.delayschedule.server.core.model.KeyValuePair;
import com.tim.delayschedule.server.core.storage.DelayTaskStorage;
import com.tim.delayschedule.server.core.storage.jdbc.dao.DelayTaskDao;
import com.tim.delayschedule.server.core.storage.jdbc.dao.impl.DelayTaskDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * jdbc的方式实现DelayTaskStorage
 */
public class JdbcDelayTaskStorage implements DelayTaskStorage {

    private DelayTaskDao delayTaskDao;

    public JdbcDelayTaskStorage(DataSource dataSource) {
        this.delayTaskDao = new DelayTaskDaoImpl(dataSource);
    }

    public JdbcDelayTaskStorage(JdbcTemplate jdbcTemplate) {
        this.delayTaskDao = new DelayTaskDaoImpl(jdbcTemplate);
    }

    public JdbcDelayTaskStorage(DelayTaskDao delayTaskDao) {
        this.delayTaskDao = delayTaskDao;
    }

    @Override
    public void addTask(ScheduleEntry scheduleEntry) {

        //TODO 逻辑判断，数据进一步封装

        TaskDaoResult result = null;

        result = delayTaskDao.insert(scheduleEntry);

        //TODO 返回结果逻辑处理
    }

    @Override
    public void addAllTask(List<ScheduleEntry> scheduleEntry) {
        delayTaskDao.insertBatch(scheduleEntry);
    }

    @Override
    public ScheduleEntry getTask(String id) {

        return delayTaskDao.select(id);
    }

    @Override
    public void markTaskExecuted(List<String> taskIdList) {

        List<KeyValuePair<String, TaskStatus>> taskIdAndStatus = new ArrayList<>() ;

        for (String id : taskIdList) {

            taskIdAndStatus.add(new KeyValuePair<>(id, TaskStatus.DELETED));
        }

        delayTaskDao.updateStatusByIdBatch(taskIdAndStatus);
    }

    @Override
    public void markTaskExecuted(String taskId) {

        delayTaskDao.updateStatusById(taskId, TaskStatus.DELETED);
    }

    @Override
    public LoadUnExecutedTaskResult loadUnExecutedTask(List<Integer> slotIdList, long cursor, long endTime) {
        return null;
    }
}
