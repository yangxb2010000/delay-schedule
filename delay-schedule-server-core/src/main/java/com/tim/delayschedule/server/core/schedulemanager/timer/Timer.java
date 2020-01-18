package com.tim.delayschedule.server.core.schedulemanager.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 定时器
 * @author xiaobing-notebook
 */
public class Timer {

    private static Logger logger = LoggerFactory.getLogger(Timer.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    /**
     * 一个Timer只有一个delayQueue
     */
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<TimerTaskList>();

    /**
     * 底层时间轮
     */
    private TimeWheel timeWheel;

    /**
     * 过期任务执行线程
     */
    private ExecutorService workerThreadPool;

    /**
     * 轮询delayQueue获取过期任务线程
     */
    private ExecutorService bossThreadPool;

    /**
     * @param tick      每层时间轮一刻度对应的时间毫秒数
     * @param wheelSize 每层时间轮的刻度数，即轮数。
     */
    public Timer(long tick, int wheelSize) {
        this(tick, wheelSize, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 2,
                new ThreadFactory() {
                    private final AtomicLong threadEpoch = new AtomicLong(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("delay-schedule-timer-workerThread-" + threadEpoch.addAndGet(1));
                        thread.setDaemon(false);
                        return thread;
                    }
                }));
    }

    /**
     * @param tick             每层时间轮一刻度对应的时间毫秒数
     * @param wheelSize        每层时间轮的刻度数，即轮数。
     * @param workerThreadPool 执行延时任务的线程池
     */
    public Timer(long tick, int wheelSize, ExecutorService workerThreadPool) {
        this.timeWheel = new TimeWheel(tick, wheelSize, System.currentTimeMillis(), delayQueue);
        this.workerThreadPool = workerThreadPool;
        this.bossThreadPool = Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("delay-schedule-timer-bossThread");
                thread.setDaemon(false);
                return thread;
            }
        });
        this.bossThreadPool.submit(() -> {
            while (true) {
                this.advanceClock(tick * wheelSize);
            }
        });
    }

    /**
     * 添加任务、如果task已经到执行时间就直接执行，如果不到就放入合适的时间轮
     */
    public void addTask(TimerTask timerTask) {
        //因为可能会把task放入时间轮中，放入时间轮时需要参考TimeWheel中的currentTime的值，所以需要加读锁
        readLock.lock();
        try {
            //向时间轮添加task返回false，就意味着该task到执行时间了
            if (!timeWheel.addTask(timerTask)) {
                workerThreadPool.submit(timerTask.getTask());
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 关闭Timer
     */
    public void shutdown() {
        this.workerThreadPool.shutdown();
        this.bossThreadPool.shutdown();
    }

    /**
     * 获取过期任务， 推进时间轮前进
     */
    private void advanceClock(long timeout) {
        TimerTaskList timerTaskList = null;
        try {
            timerTaskList = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("poll task from delayQueue interrupted, ex: ", e);
        }

        if (timerTaskList != null) {
            //因为TimeWheel是非线程安全的，因为在timeWheel.advanceClock可能会修改currentTime，所以要加写锁。
            // 而addTask的时候会对currentTime读操作，所以加读锁
            writeLock.lock();
            try {
                while (timerTaskList != null) {
                    //推动时间轮前进
                    timeWheel.advanceClock(timerTaskList.getExpiration());
                    //执行过期任务
                    timerTaskList.flush(this::addTask);
                    timerTaskList = delayQueue.poll();
                }
            } finally {
                writeLock.unlock();
            }
        }
    }
}
