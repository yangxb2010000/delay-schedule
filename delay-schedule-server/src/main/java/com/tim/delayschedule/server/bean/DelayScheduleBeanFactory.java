package com.tim.delayschedule.server.bean;

import com.tim.delayschedule.server.storage.dao.DelayTaskDao;
import com.tim.delayschedule.server.storage.dao.impl.DelayTaskDaoImpl;
import com.tim.delayschedule.server.storage.model.DbConnectInfo;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 21:32
 */
public class DelayScheduleBeanFactory {

    private static JdbcTemplate jdbcTemplate = initJdbcTemplate();

    private static DelayTaskDao delayTaskDao = initDelayTaskDao();

    private DelayScheduleBeanFactory() {
    }

    public static JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    public static DelayTaskDao getDelayTaskDao(){
        return delayTaskDao;
    }

    private static DelayTaskDaoImpl initDelayTaskDao(){
        return new DelayTaskDaoImpl();
    }

    private static DbConnectInfo initDbConnectInfo(){
        DbConnectInfo dbConnectInfo = new DbConnectInfo();

        dbConnectInfo.setUserName("root");
        dbConnectInfo.setPassword("afhjekhih322fjk");
        dbConnectInfo.setUrl("jdbc:mysql://176.122.169.95:13306/delay_schedule");
        dbConnectInfo.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dbConnectInfo;
    }

    private static BasicDataSource initDataSource(DbConnectInfo dbConnectInfo){

        if (dbConnectInfo == null){
            dbConnectInfo = initDbConnectInfo();
        }

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUsername(dbConnectInfo.getUserName());
        dataSource.setPassword(dbConnectInfo.getPassword());
        dataSource.setUrl(dbConnectInfo.getUrl());
        dataSource.setDriverClassName(dbConnectInfo.getDriverClassName());

        //初始化连接数
        dataSource.setInitialSize(5);

        return dataSource;
    }

    private static JdbcTemplate initJdbcTemplate(){
        return initJdbcTemplate(null);
    }

    private static JdbcTemplate initJdbcTemplate(BasicDataSource dataSource){

        if(dataSource == null){
            dataSource = initDataSource(null);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;

    }
}
