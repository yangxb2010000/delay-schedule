package com.tim.delayschedule.client.loadbalance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-19 22:49
 */
@AllArgsConstructor
@Data
public class ServerInstance {
    private String ip;

    private int port;
}
