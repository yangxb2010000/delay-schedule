package com.tim.delayschedule.server.storage;

import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.core.sharding.SlotRange;
import com.tim.delayschedule.server.constant.TaskDaoResult;
import com.tim.delayschedule.server.storage.dao.DelayTaskDao;
import com.tim.delayschedule.server.storage.dao.impl.DelayTaskDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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
    public void addTask(DelayTask delayTask) {

        //TODO 逻辑判断，数据进一步封装

        TaskDaoResult result = null;

        result = delayTaskDao.insert(delayTask);

        //TODO 返回结果逻辑处理
    }

    @Override
    public void addAllTask(List<DelayTask> delayTask) {
        delayTaskDao.insertBatch(delayTask);
    }

    @Override
    public DelayTask getTask(String id) {

        return delayTaskDao.select(id);
    }

    @Override
    public void markTaskExecuted(List<String> taskIdList) {

    }

    @Override
    public void markTaskExecuted(String taskId) {

        delayTaskDao.updateStatusById(taskId, TaskStatus.DELETED);
    }

    @Override
    public LoadUnExecutedTaskResult loadUnExecutedTask(SlotRange slotRange, long cursor, long endTime) {
        return null;
    }
}
