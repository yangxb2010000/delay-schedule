package com.tim.delayschedule.core.schedulemanager;

import com.tim.delayschedule.core.model.DelayTask;

/**
 * @author xiaobing
 * @date 2020/1/8
 */
public interface ScheduleManager {

    void init();

    void push(DelayTask task);
}
