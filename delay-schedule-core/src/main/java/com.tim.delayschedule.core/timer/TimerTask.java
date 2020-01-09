package com.tim.delayschedule.core.timer;

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

    public TimerTask(long delay, Runnable task) {
        this.delay = System.currentTimeMillis() + delay;
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
