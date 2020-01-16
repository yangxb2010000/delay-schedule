package com.tim.delayschedule.core.dto;

import com.tim.delayschedule.core.constant.TaskType;
import lombok.Data;


/**
 * 功能描述 : DelayTask数据传输类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:13
 */
@Data
public class DelayTaskDTO {

    /**
     * task类型
     */
    private TaskType type = TaskType.DELAY_TASK;

    /**
     * 执行task需要的参数体
     */
    private String payload;

    /**
     * task延迟时间，毫秒
     */
    private Long delayTime;

    /**
     * Job执行超时时间.单位：秒
     */
    private int ttr;

    /**
     * 执行次数
     */
    private int executedCount;

}
