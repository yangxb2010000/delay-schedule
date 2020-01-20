package com.tim.delayschedule.client.fuselimit;

import com.tim.delayschedule.client.fuselimit.model.ScheduleRateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 09:23
 */
public interface FuseLimitStrategy {
    public ScheduleRateLimiter create(double permitsPerSecond);

    public ScheduleRateLimiter create(double permitsPerSecond, long warmupPeriod);

    public ScheduleRateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit);
}
