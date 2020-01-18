package com.tim.delayschedule.server.storage.dao.impl;

import com.tim.delayschedule.server.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.server.model.Entry;
import com.tim.delayschedule.server.storage.dao.DelayTaskDao;
import com.tim.delayschedule.server.storage.mapper.DelayTaskMapper;
import com.tim.delayschedule.core.model.DelayTask;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


/**
 * 功能描述 : DelayTask持久化实现类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:38
 */
public class DelayTaskDaoImpl implements DelayTaskDao {

    private JdbcTemplate jdbcTemplate;

    public DelayTaskDaoImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DelayTaskDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DelayTask select(String id) {
        DelayTask delayTask = null;

        if (id == null){
            return delayTask;
        }

        String SQL = "select * from delay_task where id = ?";

        try {
            delayTask = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new DelayTaskMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return delayTask;
    }

    @Override
    public List<DelayTask> selectBySlotIdWithScheduleTime(List<Integer> slotIds, long scheduleTime, int pageSize, int cursor) {

        List<DelayTask> delayTasks = null;

        if (slotIds == null || slotIds.size() == 0){
            return delayTasks;
        }
        StringBuilder SQL = new StringBuilder("select * from delay_task where slot_id in (");

        for (int i = 0; i < slotIds.size(); i++){
            SQL.append(slotIds.get(i)+",");
        }

        SQL.replace(SQL.length() - 1, SQL.length(), ") ");

        SQL.append("and schedule_time <= ? limit ? offset ?");

        delayTasks = jdbcTemplate.query(SQL.toString(), new Object[]{scheduleTime, pageSize, cursor}, new DelayTaskMapper());

        return delayTasks;
    }


    @Override
    public TaskDaoResult delete(String id) {

        if (id == null){
            return TaskDaoResult.DELETE_ERROR;
        }

        String SQL = "delete from delay_task where id = ?";
        int result = jdbcTemplate.update( SQL, new Object[]{id} );

        if (result == 0){
            return TaskDaoResult.DELETE_ERROR;
        }

        return TaskDaoResult.DELETE_SUCCESS;
    }

    @Override
    public TaskDaoResult insert(DelayTask delayTask) {

        if (delayTask == null){
            return TaskDaoResult.INSERT_ERROR;
        }

        String SQL = "insert into delay_task (id, slot_id, type, payload, publish_time, schedule_time, execute_time, finished_time, " +
                "ttr, executed_count, status, create_time, update_time)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update( SQL, new Object[]{delayTask.getId(), delayTask.getSlotId(), delayTask.getType(),
                delayTask.getPayload(), delayTask.getPublishTime(), delayTask.getScheduleTime(), delayTask.getExecuteTime(),
                delayTask.getFinishedTime(), delayTask.getTtr(), delayTask.getExecutedCount(), delayTask.getStatus().toValue(),
                delayTask.getCreateTime(), delayTask.getUpdateTime()} );

        if (result == 0){
            return TaskDaoResult.INSERT_ERROR;
        }

        return TaskDaoResult.INSERT_SUCCESS;
    }

    @Override
    public TaskDaoResult insertBatch(List<DelayTask> delayTasks) {

        if (delayTasks == null){
            return TaskDaoResult.INSERT_ERROR;
        }

        String SQL = "insert into delay_task (id, slot_id, type, payload, publish_time, schedule_time, execute_time, finished_time, " +
                "ttr, executed_count, status, create_time, update_time)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DelayTask delayTask = delayTasks.get(i);

                ps.setObject(1,delayTask.getId());
                ps.setObject(2,delayTask.getSlotId());
                ps.setObject(3,delayTask.getType());
                ps.setObject(4,delayTask.getPayload());
                ps.setObject(5,delayTask.getPublishTime());
                ps.setObject(6,delayTask.getScheduleTime());
                ps.setObject(7,delayTask.getExecuteTime());
                ps.setObject(8,delayTask.getFinishedTime());
                ps.setObject(9,delayTask.getTtr());
                ps.setObject(10,delayTask.getExecutedCount());
                ps.setObject(11,delayTask.getStatus().toValue());
                ps.setObject(12,delayTask.getCreateTime());
                ps.setObject(13,delayTask.getUpdateTime());
            }

            @Override
            public int getBatchSize() {
                return delayTasks.size();
            }
        });

        return TaskDaoResult.INSERT_SUCCESS;
    }

    @Override
    public TaskDaoResult updateStatusById(String id, TaskStatus taskStatus) {

        if (id == null || taskStatus == null){
            return TaskDaoResult.UPDATE_ERROR;
        }

        String SQL = "update delay_task set status = ? where id = ?";

        int result = jdbcTemplate.update( SQL, new Object[]{taskStatus.toValue(), id} );

        if (result == 0){
            return TaskDaoResult.UPDATE_ERROR;
        }

        return TaskDaoResult.UPDATE_SUCCESS;
    }

    @Override
    public TaskDaoResult updateStatusByIdBatch(List<Entry<String, TaskStatus>> taskIdAndStatus) {
        if (taskIdAndStatus == null || taskIdAndStatus.size() == 0){
            return TaskDaoResult.UPDATE_ERROR;
        }

        String SQL = "update delay_task set status = ? where id = ?";

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Entry<String, TaskStatus> entry = taskIdAndStatus.get(i);

                ps.setObject(1, entry.getValue().toValue());
                ps.setObject(2, entry.getKey());
            }

            @Override
            public int getBatchSize() {
                return taskIdAndStatus.size();
            }
        });

        return TaskDaoResult.UPDATE_SUCCESS;
    }

}
