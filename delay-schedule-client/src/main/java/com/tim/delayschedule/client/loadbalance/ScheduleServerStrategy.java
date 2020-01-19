package com.tim.delayschedule.client.loadbalance;


import com.tim.delayschedule.client.loadbalance.model.ScheduleServer;
import com.tim.delayschedule.client.loadbalance.model.ServerInstance;

import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-19 22:06
 */
public interface ScheduleServerStrategy {

    ServerInstance getServerInstance(List<ScheduleServer> servers);

}
