package com.tim.delayschedule.core.mapper;

import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.core.model.DelayTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 14:58
 */
public class DelayTaskMapper implements RowMapper<DelayTask> {

    @Override
    public DelayTask mapRow(ResultSet rs, int rowNum) throws SQLException {

        DelayTask delayTask = new DelayTask();

        delayTask.setId(rs.getString("id"));
        delayTask.setSlotId(rs.getInt("slot_id"));
        delayTask.setType(rs.getString("type"));
        delayTask.setPayload(rs.getString("payload"));
        delayTask.setPublishTime(rs.getLong("publish_time"));
        delayTask.setScheduleTime(rs.getLong("schedule_time"));
        delayTask.setExecuteTime(rs.getLong("execute_time"));
        delayTask.setFinishedTime(rs.getLong("finished_time"));
        delayTask.setTtr(rs.getInt("ttr"));
        delayTask.setExecutedCount(rs.getInt("executed_count"));
        delayTask.setStatus(TaskStatus.fromvalue(rs.getInt("status")));

        return delayTask;
    }
}
