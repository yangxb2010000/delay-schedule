package com.tim.delayschedule.server.core.constant;

/**
 * 功能描述 : Task状态枚举类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-14 15:24
 */
public enum TaskStatus {
    /**
     * 不可执行状态，等待时钟周期
     */
    DELAY(0),

    /**
     * 可执行状态，等待消费
     */
    READY(1),

    /**
     * 已被消费者读取，但还未得到消费者的响应
     */
    RESERVED(2),

    /**
     * 已被消费完成或者已被删除
     */
    DELETED(3);

    private int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public int toValue() {
        return value;
    }

    static public TaskStatus fromvalue(int value) {
        switch (value) {
            case 0:
                return TaskStatus.DELAY;

            case 1:
                return TaskStatus.READY;

            case 2:
                return TaskStatus.RESERVED;

            case 3:
                return TaskStatus.DELETED;

            default:
                return TaskStatus.DELAY;
        }
    }

}
