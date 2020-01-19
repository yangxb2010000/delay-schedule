package com.tim.delayschedule.server.core.rpc;

import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import io.grpc.ManagedChannelBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: xiaobing
 * Date: 2020/1/19 22:31
 * Description: ScheduleServer之间相互通讯的rpc client端实现
 */
public class ScheduleClient {
    private SlotSharding slotSharding;

    private ConcurrentHashMap<Integer, ScheduleServiceStubWrapper> slot2Stub = new ConcurrentHashMap<Integer, ScheduleServiceStubWrapper>();

    public ScheduleClient(SlotSharding slotSharding) {
        this.slotSharding = slotSharding;

        this.slotSharding.registerServer2SlotChangeListener(server2SlotMap -> {

        });
    }

    public ScheduleServerGrpc.PushTaskReply.ResultCode push(ScheduleServerGrpc.PushTaskRequest pushRequest, int slotId) {
        ScheduleServiceGrpc.ScheduleServiceBlockingStub stub = getStub(slotId);
        if (stub == null) {
            throw new RuntimeException("can not found schedule service stub for slotId: " + slotId);
        }

        ScheduleServerGrpc.PushTaskReply pushTaskReply = stub.pushTask(pushRequest);
        return pushTaskReply.getResultCode();
    }

    private ScheduleServiceGrpc.ScheduleServiceBlockingStub getStub(int slotId) {
        ScheduleServiceStubWrapper wrapper = slot2Stub.get(slotId);
        if (wrapper != null) {
            return wrapper.getStub();
        }

        return null;
    }


    @Builder
    @Data
    private static class ScheduleServiceStubWrapper {
        private String ipAndPort;
        private ScheduleServiceGrpc.ScheduleServiceBlockingStub stub;
    }

    private ScheduleServiceGrpc.ScheduleServiceBlockingStub createScheduleServiceStub(String host, int port) {
        return ScheduleServiceGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }
}
