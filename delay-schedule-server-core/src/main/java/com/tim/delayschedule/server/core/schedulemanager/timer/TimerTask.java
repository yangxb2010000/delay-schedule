package com.tim.delayschedule.server.core.schedulemanager.timer;

/**
 * 任务
 */
public class TimerTask {

    /**
     * 延迟时间
     */
    private long delay;

    /**
     * 任务
     */
    private Runnable task;

    /**
     * 下一个节点
     */
    protected TimerTask next;

    /**
     * 上一个节点
     */
    protected TimerTask pre;

    /**
     * 构建TimerTask实例
     * @param scheduleTime task计划执行时间，ms
     * @param task 执行的task
     */
    public TimerTask(long scheduleTime, Runnable task) {
        this.delay = scheduleTime;
        this.task = task;
        this.next = null;
        this.pre = null;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delay;
    }
}
