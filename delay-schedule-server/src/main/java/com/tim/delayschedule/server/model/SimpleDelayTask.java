package com.tim.delayschedule.server.model;

import lombok.Data;

/**
 * @author xiaobing
 * @date 2020/1/16
 */
@Data
public class SimpleDelayTask {
    /**
     * id task的唯一标识
     */
    private String id;

    /**
     * slotId
     */
    private int slotId;

    /**
     * task计划执行时间
     */
    private Long scheduleTime;

}
