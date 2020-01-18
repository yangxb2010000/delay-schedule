package com.tim.delayschedule.utils;

import com.tim.delayschedule.server.core.storage.model.DbConnectInfo;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 功能描述 : TODO sql链接测试使用，后续删除
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 21:32
 */
public class DataSourceUtils {

    private static DbConnectInfo initDbConnectInfo(){
        DbConnectInfo dbConnectInfo = new DbConnectInfo();

        dbConnectInfo.setUserName("root");
        dbConnectInfo.setPassword("afhjekhih322fjk");
        dbConnectInfo.setUrl("jdbc:mysql://176.122.169.95:13306/delay_schedule");
        dbConnectInfo.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dbConnectInfo;
    }

    public static BasicDataSource initDataSource(){

        DbConnectInfo dbConnectInfo = initDbConnectInfo();

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUsername(dbConnectInfo.getUserName());
        dataSource.setPassword(dbConnectInfo.getPassword());
        dataSource.setUrl(dbConnectInfo.getUrl());
        dataSource.setDriverClassName(dbConnectInfo.getDriverClassName());

        //初始化连接数
        dataSource.setInitialSize(5);

        return dataSource;
    }
}
