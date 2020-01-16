package com.tim.delayschedule.core.constant;

/**
 * 功能描述 : Task类型枚举类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 14:15
 */
public enum TaskDaoResult {
    /**
     * 数据插入失败
     */
    INSERT_ERROR(0),

    /**
     * 数据插入成功
     */
    INSERT_SUCCESS(1),

    /**
     * 数据更新失败
     */
    UPDATE_ERROR(2),

    /**
     * 数据更新成功
     */
    UPDATE_SUCCESS(3),

    /**
     * 数据删除失败
     */
    DELETE_ERROR(4),

    /**
     * 数据删除成功
     */
    DELETE_SUCCESS(5),

    /**
     * 占位
     */
    NULL(9999);

    private int value;

    TaskDaoResult(int value) {
        this.value = value;
    }

    public int toValue() {
        return value;
    }

    static public TaskDaoResult fromValue(int value) {
        switch (value) {
            case 0:
                return TaskDaoResult.INSERT_ERROR;

            case 1:
                return TaskDaoResult.INSERT_SUCCESS;

            case 2:
                return TaskDaoResult.UPDATE_ERROR;

            case 3:
                return TaskDaoResult.UPDATE_SUCCESS;

            case 4:
                return TaskDaoResult.DELETE_ERROR;

            case 5:
                return TaskDaoResult.DELETE_SUCCESS;

            default:
                return TaskDaoResult.NULL;
        }
    }

}
