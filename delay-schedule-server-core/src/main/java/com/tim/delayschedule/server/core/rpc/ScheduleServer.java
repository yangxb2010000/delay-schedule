package com.tim.delayschedule.server.core.rpc;

import com.tim.delayschedule.server.core.schedulemanager.ScheduleManager;
import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

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


    static class ScheduleServiceImpl extends ScheduleServiceGrpc.ScheduleServiceImplBase {
        private ScheduleManager scheduleManager;
        private SlotSharding slotSharding;

        public ScheduleServiceImpl(ScheduleManager scheduleManager,
                                   SlotSharding slotSharding) {
            this.scheduleManager = scheduleManager;
            this.slotSharding = slotSharding;
        }

        @Override
        public void pushTask(ScheduleServerGrpc.PushTaskRequest request, StreamObserver<ScheduleServerGrpc.PushTaskReply> responseObserver) {
            ScheduleServerGrpc.PushTaskReply.ResultCode resultCode = scheduleManager.push(request);
            responseObserver.onNext(ScheduleServerGrpc.PushTaskReply.newBuilder().setResultCode(resultCode).build());
            responseObserver.onCompleted();
        }

        @Override
        public void releaseSlot(ScheduleServerGrpc.ReleaseSlotRequest request, StreamObserver<ScheduleServerGrpc.ReleaseSlotReply> responseObserver) {
            slotSharding.releaseSlot(request.getSlotId(), request.getSlotChangeVersion());
            responseObserver.onNext(ScheduleServerGrpc.ReleaseSlotReply.newBuilder().setIsSuccess(true).build());
            responseObserver.onCompleted();
        }
    }
}