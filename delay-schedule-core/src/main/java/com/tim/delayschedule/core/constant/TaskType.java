package com.tim.delayschedule.core.constant;

/**
 * 功能描述 : Task类型枚举类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-14 13:15
 */
public enum TaskType {
    /**
     * 延迟队列
     */
    DELAY_TASK(0);


    private int value;

    TaskType(int value) {
        this.value = value;
    }

    public int toValue() {
        return value;
    }

    static public TaskType fromValue(int value) {
        switch (value) {
            case 0:
                return TaskType.DELAY_TASK;

            default:
                return TaskType.DELAY_TASK;
        }
    }

}
