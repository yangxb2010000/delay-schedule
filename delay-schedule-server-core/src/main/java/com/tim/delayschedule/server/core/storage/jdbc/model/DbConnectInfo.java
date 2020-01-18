package com.tim.delayschedule.server.core.storage.jdbc.model;

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
     * 数据库url
     */
    private String url;

    /**
     * 数据库驱动
     */
    private String driverClassName;


}
