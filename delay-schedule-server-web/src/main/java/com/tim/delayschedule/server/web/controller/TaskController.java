package com.tim.delayschedule.server.web.controller;

import com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc;
import com.tim.delayschedule.server.core.schedulemanager.ScheduleManager;
import com.tim.delayschedule.server.web.model.PushTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaobing
 * @date 2020/1/20
 */
@RestController
@RequestMapping("/")
public class TaskController {
    @Autowired
    private ScheduleManager scheduleManager;

    @RequestMapping("/push")
    public ScheduleServerGrpc.PushTaskReply.ResultCode pushTask(@RequestBody PushTaskRequest pushTaskRequest) {
        if (pushTaskRequest == null) {
            throw new RuntimeException("failed to build PushTaskRequest from request body. please check the request body format.");
        }

        ScheduleServerGrpc.PushTaskReply.ResultCode resultCode = scheduleManager.push(toGrpcPushTaskRequest(pushTaskRequest));
        return resultCode;
    }

    private ScheduleServerGrpc.PushTaskRequest toGrpcPushTaskRequest(PushTaskRequest pushTaskRequest) {
        return ScheduleServerGrpc.PushTaskRequest
                .newBuilder()
                .setId(pushTaskRequest.getId())
                .setPayload(pushTaskRequest.getPayload())
                .setType(pushTaskRequest.getType())
                .setScheduleTime(pushTaskRequest.getScheduleTime())
                .build();
    }
}
