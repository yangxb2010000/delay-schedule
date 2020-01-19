package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.model.ScheduleEntry;

/**
 * @author xiaobing
 * @date 2020/1/8
 */
public interface ScheduleManager {

    /**
     * 做一些初始化准备工作
     */
    void init();

    /**
     * 添加Schedule entry
     *
     * @param task
     */
    void push(ScheduleEntry task);

    enum PushScheduleEntryResult {
        /**
         * 处理成功
         */
        SUCCESS(0),

        /**
         * 处理失败，当前ScheduleServer不负责该ScheduleEntry
         */
        FAIL_NOTHANDLED(1);

        private int value;

        PushScheduleEntryResult(int value) {
            this.value = value;
        }

        public int toValue() {
            return value;
        }
    }
}
