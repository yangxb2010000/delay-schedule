package com.tim.delayschedule.server.web.utils;

import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.web.dto.ScheduleEntryDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述 : class类型转换工具类
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-20 09:58
 */
public class ClassConverUtils {

    /**
     * 格式化时间
     */
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static ScheduleEntry ScheduleEntryDTO2ScheduleEntry(ScheduleEntryDTO scheduleEntryDTO){
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        Date date = new Date();

        String currentTime = format.format(date);

        scheduleEntry.setId(UUID.randomUUID().toString());
        scheduleEntry.setPayload(scheduleEntryDTO.getPayload());
        scheduleEntry.setStatus(TaskStatus.NEW);
        scheduleEntry.setTtr(scheduleEntryDTO.getTtr());
        scheduleEntry.setType(scheduleEntryDTO.getType());
        scheduleEntry.setPublishTime(date.getTime());
//        scheduleEntry.setScheduleTime(date.getTime() + scheduleEntryDTO.getDelayTime());
//        scheduleEntry.setCreateTime(currentTime);
//        scheduleEntry.setUpdateTime(currentTime);
        scheduleEntry.setExecutedCount(scheduleEntryDTO.getExecutedCount());

        return scheduleEntry;
    }
}
