package com.tim.delayschedule.client.loadbalance;

import com.tim.delayschedule.client.loadbalance.model.ScheduleWeightServer;
import com.tim.delayschedule.client.loadbalance.model.ServerInstance;
import com.tim.delayschedule.client.loadbalance.strategy.SmoothWeightSchedule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScheduleServerStrategyTest {
    private ScheduleServerStrategy scheduleServerStrategy = new SmoothWeightSchedule();

    @Test
    public void getServerInstance() {


        List<ScheduleWeightServer> servers = new ArrayList<>();

        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.1",1),1));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.2",1),2));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.3",1),3));
        servers.add(new ScheduleWeightServer(new ServerInstance("127.0.0.4",1),4));

        for (int i = 0; i < 10; i++){
            ServerInstance serverInstance = scheduleServerStrategy.getServerInstance(servers);
            System.out.println(serverInstance.getIp());
        }

    }
}