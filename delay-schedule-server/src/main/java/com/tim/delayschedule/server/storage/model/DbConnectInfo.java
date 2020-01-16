package com.tim.delayschedule.server.storage.model;

import lombok.Data;

/**
 * 功能描述 : 数据库连接对象
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:55
 */
@Data
public class DbConnectInfo {

    /**
     * 数据库账户
     */
    private String userName;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库地址
     */
    private String host;

    /**
     * 数据库端口
     */
    private String port;

    /**
     * 数据库驱动
     */
    private String driverClassName;


}
