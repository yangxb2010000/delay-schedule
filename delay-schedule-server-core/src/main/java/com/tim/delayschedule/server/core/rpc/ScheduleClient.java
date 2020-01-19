package com.tim.delayschedule.server.core.rpc;

import com.tim.delayschedule.server.core.model.ScheduleEntry;

public interface ScheduleClient {
    void push(ScheduleEntry task);
}
