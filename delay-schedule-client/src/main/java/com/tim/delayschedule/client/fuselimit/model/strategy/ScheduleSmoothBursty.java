package com.tim.delayschedule.client.fuselimit.model.strategy;

import com.google.common.util.concurrent.RateLimiter;
import com.tim.delayschedule.client.fuselimit.FuseLimitStrategy;
import com.tim.delayschedule.client.fuselimit.model.ScheduleRateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 09:11
 */
public class ScheduleSmoothBursty implements FuseLimitStrategy {

    @Override
    public ScheduleRateLimiter create(double permitsPerSecond) {
        RateLimiter limiter = RateLimiter.create(permitsPerSecond);

        return new ScheduleRateLimiter(limiter);
    }

    @Override
    public ScheduleRateLimiter create(double permitsPerSecond, long warmupPeriod) {
        return create(permitsPerSecond, warmupPeriod, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduleRateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
        RateLimiter limiter = RateLimiter.create(permitsPerSecond, warmupPeriod, unit);

        return new ScheduleRateLimiter(limiter);
    }
}
