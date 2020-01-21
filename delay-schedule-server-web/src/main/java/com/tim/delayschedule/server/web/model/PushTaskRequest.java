package com.tim.delayschedule.server.web.model;

import lombok.Data;

/**
 * @author xiaobing
 * @date 2020/1/20
 */
@Data
public class PushTaskRequest {
    private String id;
    /**
     * task类型
     */
    private String type;
    /**
     * 执行task需要的参数体
     */
    private String payload;

    /**
     * 预计执行时间
     */
    private long scheduleTime;
}
