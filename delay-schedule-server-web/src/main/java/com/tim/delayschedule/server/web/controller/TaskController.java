package com.tim.delayschedule.server.web.controller;

import com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc;
import com.tim.delayschedule.server.core.schedulemanager.ScheduleManager;
import com.tim.delayschedule.server.web.model.PushTaskRestRequest;
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
    public ScheduleServerGrpc.PushTaskReply.ResultCode pushTask(@RequestBody PushTaskRestRequest pushTaskRestRequest) {
        if (pushTaskRestRequest == null) {
            throw new RuntimeException("failed to build PushTaskRequest from request body. please check the request body format.");
        }

        ScheduleServerGrpc.PushTaskReply.ResultCode resultCode = scheduleManager.push(toRpcPushTaskRequest(pushTaskRestRequest));
        return resultCode;
    }

    private ScheduleServerGrpc.PushTaskRequest toRpcPushTaskRequest(PushTaskRestRequest pushTaskRestRequest) {
        return ScheduleServerGrpc.PushTaskRequest
                .newBuilder()
                .setId(pushTaskRestRequest.getId())
                .setPayload(pushTaskRestRequest.getPayload())
                .setType(pushTaskRestRequest.getType())
                .setScheduleTime(pushTaskRestRequest.getScheduleTime())
                .build();
    }
}
