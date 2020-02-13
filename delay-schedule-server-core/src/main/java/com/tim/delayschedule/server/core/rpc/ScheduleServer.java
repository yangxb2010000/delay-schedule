package com.tim.delayschedule.server.core.rpc;

import com.tim.delayschedule.server.core.schedulemanager.ScheduleManager;
import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * User: xiaobing
 * Date: 2020/1/19 22:31
 * Description: ScheduleServer之间相互通讯的rpc server端实现
 */
public class ScheduleServer {
    private ScheduleServiceImpl scheduleService;
    private Server server;
    private int port;

    public ScheduleServer(ScheduleManager scheduleManager, SlotSharding slotSharding) {
        this(scheduleManager, slotSharding, 10190);
    }

    public ScheduleServer(ScheduleManager scheduleManager, SlotSharding slotSharding, int port) {
        scheduleService = new ScheduleServiceImpl(scheduleManager, slotSharding);
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(this.port)
                .addService(this.scheduleService)
                .build()
                .start();
    }

    public void close() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }


}