package com.tim.delayschedule.server.core.storage.jdbc.dao.impl;

import com.tim.delayschedule.server.core.constant.TaskDaoResult;
import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.KeyValuePair;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.storage.jdbc.dao.DelayTaskDao;
import com.tim.delayschedule.server.core.storage.jdbc.mapper.DelayTaskMapper;
import com.tim.delayschedule.server.core.storage.jdbc.mapper.SimpleScheduleEntryDbMapper;
import com.tim.delayschedule.server.core.storage.jdbc.model.SimpleScheduleEntryDb;
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
    public ScheduleEntry select(String id) {
        ScheduleEntry scheduleEntry = null;

        if (id == null){
            return scheduleEntry;
        }

        String SQL = "select * from delay_task where id = ?";

        try {
            scheduleEntry = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new DelayTaskMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return scheduleEntry;
    }

    @Override
    public List<SimpleScheduleEntryDb> selectBySlotIdWithScheduleTime(List<Integer> slotIds, long cursor, long scheduleTime) {

        List<SimpleScheduleEntryDb> delayTasks = null;

        if (slotIds == null || slotIds.size() == 0){
            return delayTasks;
        }
        StringBuilder SQL = new StringBuilder("select record_id, id, slot_id, schedule_time from delay_task where slot_id in (");

        for (int i = 0; i < slotIds.size(); i++){
            SQL.append(slotIds.get(i)+",");
        }

        SQL.replace(SQL.length() - 1, SQL.length(), ") ");

        SQL.append("and record_id >= ? and schedule_time <= ? and status = ?");

        delayTasks = jdbcTemplate.query(SQL.toString(), new Object[]{cursor, scheduleTime, TaskStatus.NEW.toValue()}, new SimpleScheduleEntryDbMapper());

        return delayTasks;
    }


    @Override
    public TaskDaoResult delete(String id) {

        if (id == null){
            return TaskDaoResult.DELETE_FAIL;
        }

        String SQL = "delete from delay_task where id = ?";
        int result = jdbcTemplate.update( SQL, new Object[]{id} );

        if (result == 0){
            return TaskDaoResult.DELETE_FAIL;
        }

        return TaskDaoResult.DELETE_SUCCESS;
    }

    @Override
    public TaskDaoResult insert(ScheduleEntry scheduleEntry) {

        if (scheduleEntry == null){
            return TaskDaoResult.INSERT_FAIL;
        }

        String SQL = "insert into delay_task (id, slot_id, type, payload, publish_time, schedule_time, execute_time, finished_time, " +
                "ttr, executed_count, status, create_time, update_time)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update( SQL, new Object[]{scheduleEntry.getId(), scheduleEntry.getSlotId(), scheduleEntry.getType(),
                scheduleEntry.getPayload(), scheduleEntry.getPublishTime(), scheduleEntry.getNextScheduleTime(), scheduleEntry.getExecuteTime(),
                scheduleEntry.getFinishedTime(), scheduleEntry.getTtr(), scheduleEntry.getExecutedCount(), scheduleEntry.getStatus().toValue(),
                scheduleEntry.getCreateTime(), scheduleEntry.getUpdateTime()} );

        if (result == 0){
            return TaskDaoResult.INSERT_FAIL;
        }

        return TaskDaoResult.INSERT_SUCCESS;
    }

    @Override
    public TaskDaoResult insertBatch(List<ScheduleEntry> scheduleEntries) {

        if (scheduleEntries == null){
            return TaskDaoResult.INSERT_FAIL;
        }

        String SQL = "insert into delay_task (id, slot_id, type, payload, publish_time, schedule_time, execute_time, finished_time, " +
                "ttr, executed_count, status, create_time, update_time)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ScheduleEntry scheduleEntry = scheduleEntries.get(i);

                ps.setObject(1, scheduleEntry.getId());
                ps.setObject(2, scheduleEntry.getSlotId());
                ps.setObject(3, scheduleEntry.getType());
                ps.setObject(4, scheduleEntry.getPayload());
                ps.setObject(5, scheduleEntry.getPublishTime());
                ps.setObject(6, scheduleEntry.getNextScheduleTime());
                ps.setObject(7, scheduleEntry.getExecuteTime());
                ps.setObject(8, scheduleEntry.getFinishedTime());
                ps.setObject(9, scheduleEntry.getTtr());
                ps.setObject(10, scheduleEntry.getExecutedCount());
                ps.setObject(11, scheduleEntry.getStatus().toValue());
                ps.setObject(12, scheduleEntry.getCreateTime());
                ps.setObject(13, scheduleEntry.getUpdateTime());
            }

            @Override
            public int getBatchSize() {
                return scheduleEntries.size();
            }
        });

        return TaskDaoResult.INSERT_SUCCESS;
    }

    @Override
    public TaskDaoResult updateStatusById(String id, TaskStatus taskStatus) {

        if (id == null || taskStatus == null){
            return TaskDaoResult.UPDATE_FAIL;
        }

        String SQL = "update delay_task set status = ?, update_time = ? where id = ?";
        long currentTime = System.currentTimeMillis();

        int result = jdbcTemplate.update( SQL, new Object[]{taskStatus.toValue(),currentTime, id} );

        if (result == 0){
            return TaskDaoResult.UPDATE_FAIL;
        }

        return TaskDaoResult.UPDATE_SUCCESS;
    }

    @Override
    public TaskDaoResult updateStatusByIdBatch(List<KeyValuePair<String, TaskStatus>> taskIdAndStatus) {
        if (taskIdAndStatus == null || taskIdAndStatus.size() == 0){
            return TaskDaoResult.UPDATE_FAIL;
        }

        String SQL = "update delay_task set status = ?, update_time = ? where id = ?";
        long currentTime = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                KeyValuePair<String, TaskStatus> keyValuePair = taskIdAndStatus.get(i);

                ps.setObject(1, keyValuePair.getValue().toValue());
                ps.setObject(2, currentTime);
                ps.setObject(3, keyValuePair.getKey());
            }

            @Override
            public int getBatchSize() {
                return taskIdAndStatus.size();
            }
        });

        return TaskDaoResult.UPDATE_SUCCESS;
    }

}
