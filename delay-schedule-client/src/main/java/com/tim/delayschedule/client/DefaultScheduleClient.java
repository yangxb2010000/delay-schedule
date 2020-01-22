package com.tim.delayschedule.client;

import com.tim.delayschedule.client.configuration.ScheduleServerConfiguration;
import com.tim.delayschedule.client.fuselimit.FuseLimitStrategy;
import com.tim.delayschedule.client.loadbalance.ScheduleServerStrategy;
import com.tim.delayschedule.client.loadbalance.model.ServerInstance;
import com.tim.delayschedule.client.temp.ScheduleEntry;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 10:52
 */
@Data
@AllArgsConstructor
public class DefaultScheduleClient implements ScheduleClient{

    /**
     * 负载均衡策略
     */
    private ScheduleServerStrategy scheduleServerStrategy;

    /**
     * 限流策略
     */
    private FuseLimitStrategy fuseLimitStrategy;

    @Override
    public boolean addScheduleEntry(ScheduleEntry scheduleEntry) {

        boolean sendServerResult = false;
        int retryCount = ScheduleServerConfiguration.retryCount;

        //判断是否需要限流


        //防止server挂掉，知道发送成功
        //极端情况下，会一直选到该服务器，应加重试次数retryCount
        while (!sendServerResult && retryCount-- != 0){
            //找到合适的server地址
            ServerInstance server = scheduleServerStrategy.getServerInstance(
                    ScheduleServerConfiguration.scheduleWeightServers);

            //与server端通信，将该任务信息发送server端

        }

        return true;
    }
}
