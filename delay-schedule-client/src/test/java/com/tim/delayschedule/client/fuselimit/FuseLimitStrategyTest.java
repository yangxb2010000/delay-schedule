package com.tim.delayschedule.client.fuselimit;

import com.google.common.util.concurrent.RateLimiter;
import com.tim.delayschedule.client.fuselimit.strategy.ScheduleSmoothBursty;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FuseLimitStrategyTest {


    @Test
    public void create() {
        FuseLimitStrategy fuseLimitStrategy = new ScheduleSmoothBursty();
        RateLimiter limiter = fuseLimitStrategy.create(5).getRateLimiter();

        System.out.println(limiter.acquire(10));
        if (limiter.tryAcquire(1, 10000, TimeUnit.MILLISECONDS)){
            System.out.println("可以获取");
        }
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));

        //测试冷启动模式
        System.out.println("-----测试冷启动模式-----");
        limiter = fuseLimitStrategy.create(5, 1000, TimeUnit.MILLISECONDS).getRateLimiter();

        for(int i = 1; i < 5;i++) {
            System.out.println(limiter.acquire());
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 1; i < 5;i++) {
            System.out.println(limiter.acquire());
        }

    }
}