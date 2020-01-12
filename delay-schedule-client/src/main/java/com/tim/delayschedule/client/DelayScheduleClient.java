package com.tim.delayschedule.client;

import com.tim.delayschedule.core.model.DelayTask;

public interface DelayScheduleClient {
    void addTask(DelayTask delayTask);
}
