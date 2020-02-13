package com.tim.delayschedule.server.core.rpc;

import com.tim.delayschedule.server.core.schedulemanager.ScheduleManager;
import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import io.grpc.stub.StreamObserver;

/**
 * User: xiaobing
 * Date: 2020/2/13 16:05
 * Description:
 */
class ScheduleServiceImpl extends ScheduleServiceGrpc.ScheduleServiceImplBase {
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