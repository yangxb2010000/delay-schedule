package com.tim.delayschedule.core.dao.impl;

import com.tim.delayschedule.core.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.core.dao.DelayTaskDao;
import com.tim.delayschedule.core.mapper.DelayTaskMapper;
import com.tim.delayschedule.core.model.DbConnectInfo;
import com.tim.delayschedule.core.model.DelayTask;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:38
 */
public class DelayTaskDaoImpl implements DelayTaskDao {

    private JdbcTemplate jdbcTemplate;

    public DelayTaskDaoImpl(){

        this.jdbcTemplate = initJdbcTemplate();

    }

    public DelayTaskDaoImpl(DbConnectInfo dbConnectInfo) {

        if (dbConnectInfo == null){
            dbConnectInfo = initDbConnectInfo();
        }

        BasicDataSource dataSource = initDataSource(dbConnectInfo);

        this.jdbcTemplate = initJdbcTemplate(dataSource);

    }

    public DelayTaskDaoImpl(JdbcTemplate jdbcTemplate) {

        if (jdbcTemplate == null){
            jdbcTemplate = initJdbcTemplate();
        }

        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public DelayTask select(String id) {

        String SQL = "select * from delay_task where id = ?";
        DelayTask delayTask = null;

        try {
            delayTask = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new DelayTaskMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return delayTask;
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

    private DbConnectInfo initDbConnectInfo(){
        DbConnectInfo dbConnectInfo = new DbConnectInfo();

        dbConnectInfo.setUserName("root");
        dbConnectInfo.setPassword("afhjekhih322fjk");
        dbConnectInfo.setHost("176.122.169.95");
        dbConnectInfo.setPort("13306");
        dbConnectInfo.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dbConnectInfo;
    }

    private BasicDataSource initDataSource(DbConnectInfo dbConnectInfo){

        if (dbConnectInfo == null){
            dbConnectInfo = initDbConnectInfo();
        }

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUsername(dbConnectInfo.getUserName());
        dataSource.setPassword(dbConnectInfo.getPassword());
        dataSource.setUrl(String.format("jdbc:mysql://%s:%s/delay_schedule", dbConnectInfo.getHost(), dbConnectInfo.getPort()));
        dataSource.setDriverClassName(dbConnectInfo.getDriverClassName());

        //初始化连接数
        dataSource.setInitialSize(5);

        return dataSource;
    }

    private JdbcTemplate initJdbcTemplate(){
        return initJdbcTemplate(null);
    }

    private JdbcTemplate initJdbcTemplate(BasicDataSource dataSource){

        if(dataSource == null){
            dataSource = initDataSource(null);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;

    }
}
