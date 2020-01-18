package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.model.ScheduleEntry;

/**
 * @author xiaobing
 * @date 2020/1/8
 */
public interface ScheduleManager {

    void init();

    void push(ScheduleEntry task);
}
