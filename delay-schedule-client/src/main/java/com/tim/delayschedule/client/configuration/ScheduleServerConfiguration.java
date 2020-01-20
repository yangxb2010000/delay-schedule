package com.tim.delayschedule.client.configuration;

import com.tim.delayschedule.client.loadbalance.model.ScheduleWeightServer;
import com.tim.delayschedule.client.loadbalance.model.ServerInstance;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 11:13
 */
@Data
public class ScheduleServerConfiguration {

    public static List<ScheduleWeightServer> scheduleWeightServers = initScheduleWeightServers();

    private static List<ScheduleWeightServer> initScheduleWeightServers(){

        //TODO 后期改为读取配置文件
        List<ScheduleWeightServer> servers = new ArrayList<>();

        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.1",1),1));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.2",1),2));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.3",1),3));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.4",1),4));

        return servers;
    }
}
