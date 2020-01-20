package com.tim.delayschedule.client.fuselimit.model;

import com.google.common.util.concurrent.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO 对熔断限流算法进行封装，因为直接使用第三方算法，暂时使用适配器模式
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 09:23
 */
@Data
@AllArgsConstructor
public class ScheduleRateLimiter  {

    private RateLimiter rateLimiter;

}
