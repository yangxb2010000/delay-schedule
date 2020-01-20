package com.tim.delayschedule.server.web.dto;

import lombok.Data;


/**
 * 功能描述 : DelayTask数据传输类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:13
 */
@Data
public class ScheduleEntryDTO {

    /**
     * task类型
     */
    private String type;

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
