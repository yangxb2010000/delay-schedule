package com.tim.delayschedule.client.loadbalance.model;

import lombok.Data;

/**
 * ScheduleServer平滑调度POJO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-19 17:27
 */
@Data
public class ScheduleWeightServer {

    /**
     * 服务器节点信息
     */
    private ServerInstance server;

    /**
     * 初始权重
     */
    private Integer originalWeight;

    /**
     * 当前权重
     */
    private Integer currentWeight;

    public ScheduleWeightServer(ServerInstance server, Integer originalWeight) {
        this.server = server;
        this.originalWeight = originalWeight;
        this.currentWeight = originalWeight;
    }
}
