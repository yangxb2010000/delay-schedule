package com.tim.delayschedule.server.core.schedulemanager.timer;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author xiaobing
 * @date 2020/1/13
 */
public class TimerTest {

    @Test
    public void addTask() throws Throwable {
        Timer timer = new Timer(10, 100);

        AtomicInteger taskCount = new AtomicInteger();

        List<Throwable> assertExceptionList = new CopyOnWriteArrayList<>();

        Function<Integer, TimerTask> assertTimerTask = (delay) -> {
            taskCount.incrementAndGet();

            long start = System.nanoTime();
            return new TimerTask(System.currentTimeMillis() + delay, new Runnable() {
                @Override
                public void run() {
                    taskCount.decrementAndGet();
                    long delayTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
                    try {
                        //允许的延迟任务误差20ms
                        Assert.assertTrue(delayTime > delay - 20 && delayTime < delay + 20);
                    } catch (Throwable ex) {
                        System.out.println("expected delay + " + delay + ", delayTime: " + delayTime);
                        ex.printStackTrace();
                        assertExceptionList.add(ex);
                    }
                }
            });
        };

        timer.addTask(assertTimerTask.apply(1000));
        timer.addTask(assertTimerTask.apply(2000));
        timer.addTask(assertTimerTask.apply(5000));
        timer.addTask(assertTimerTask.apply(1000));
        timer.addTask(assertTimerTask.apply(1500));
        timer.addTask(assertTimerTask.apply(2500));

        Thread.sleep(6000);
        timer.shutdown();

        for (Throwable ex : assertExceptionList) {
            throw ex;
        }

        Assert.assertEquals(taskCount.get(), 0);
    }
}