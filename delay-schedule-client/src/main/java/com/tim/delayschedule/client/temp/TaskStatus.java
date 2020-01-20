package com.tim.delayschedule.client.temp;

/**
 * 功能描述 : Task状态枚举类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-14 15:24
 */
public enum TaskStatus {
    /**
     * 新任务，不可执行状态，等待时钟周期
     */
    NEW(0),

    /**
     * 完成状态
     */
    FINISH(1),

    /**
     * 删除
     */
    DELETED(2);

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
                return TaskStatus.NEW;

            case 1:
                return TaskStatus.FINISH;

            case 2:
                return TaskStatus.DELETED;

            default:
                return TaskStatus.NEW;
        }
    }

}
