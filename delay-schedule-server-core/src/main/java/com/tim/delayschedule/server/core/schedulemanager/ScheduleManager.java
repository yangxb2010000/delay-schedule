package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc;

/**
 * @author xiaobing
 * @date 2020/1/8
 */
public interface ScheduleManager {

    /**
     * 做一些初始化准备工作
     */
    void init();

    /**
     * 添加Schedule entry
     *
     * @param task
     */
    ScheduleServerGrpc.PushTaskReply.ResultCode push(ScheduleServerGrpc.PushTaskRequest task);
}
