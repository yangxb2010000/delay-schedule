package com.tim.delayschedule.server.core.storage.mapper;

import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 功能描述 : DelayTask持久化映射对象
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 14:58
 */
public class DelayTaskMapper implements RowMapper<ScheduleEntry> {

    @Override
    public ScheduleEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

        ScheduleEntry scheduleEntry = new ScheduleEntry();

        scheduleEntry.setId(rs.getString("id"));
        scheduleEntry.setSlotId(rs.getInt("slot_id"));
        scheduleEntry.setType(rs.getString("type"));
        scheduleEntry.setPayload(rs.getString("payload"));
        scheduleEntry.setPublishTime(rs.getLong("publish_time"));
        scheduleEntry.setScheduleTime(rs.getLong("schedule_time"));
        scheduleEntry.setExecuteTime(rs.getLong("execute_time"));
        scheduleEntry.setFinishedTime(rs.getLong("finished_time"));
        scheduleEntry.setTtr(rs.getInt("ttr"));
        scheduleEntry.setExecutedCount(rs.getInt("executed_count"));
        scheduleEntry.setStatus(TaskStatus.fromvalue(rs.getInt("status")));

        return scheduleEntry;
    }
}
