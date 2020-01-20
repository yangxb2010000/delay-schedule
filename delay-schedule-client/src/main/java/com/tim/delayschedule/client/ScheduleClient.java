package com.tim.delayschedule.client;


import com.tim.delayschedule.client.temp.ScheduleEntry;

public interface ScheduleClient {

    /**
     * TODO
     * @Param : [scheduleEntry]
     * @Return : true 发送成功
     */
    boolean addScheduleEntry(ScheduleEntry scheduleEntry);

}
