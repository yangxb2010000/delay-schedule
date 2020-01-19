package com.tim.delayschedule.client.loadbalance.strategy;

import com.tim.delayschedule.client.loadbalance.ScheduleServerStrategy;
import com.tim.delayschedule.client.loadbalance.model.ScheduleServer;
import com.tim.delayschedule.client.loadbalance.model.ServerInstance;

import java.util.*;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-19 17:29
 */
public class SmoothWeightSchedule implements ScheduleServerStrategy {

    @Override
    public ServerInstance getServerInstance(List<ScheduleServer> servers) {
        if (servers == null || servers.size() == 0){
            return null;
        }

        // 原始权重之和
        int weightSum = 0;
        // 最大当前权重对象
        ScheduleServer maxWeightServer = servers.get(0);

        // 计算最大当前权重对象，同时求原始权重之和
        for (ScheduleServer scheduleServer : servers){
            weightSum += scheduleServer.getOriginalWeight();
            if (scheduleServer.getCurrentWeight() > maxWeightServer.getCurrentWeight()){
                maxWeightServer = scheduleServer;
            }
        }

        /**
         * 重新调整 currentWeight 权重：
         * maxWeightServer.currentWeight -= weightSum
         * 每个 smoothWeightServer.currentWeight += smoothWeightServer.originalWeight
         */
        maxWeightServer.setCurrentWeight(maxWeightServer.getCurrentWeight() - weightSum);

        for (ScheduleServer scheduleServer : servers){
            scheduleServer.setCurrentWeight(scheduleServer.getCurrentWeight() + scheduleServer.getOriginalWeight());
        }

        return maxWeightServer.getServer();
    }
}
