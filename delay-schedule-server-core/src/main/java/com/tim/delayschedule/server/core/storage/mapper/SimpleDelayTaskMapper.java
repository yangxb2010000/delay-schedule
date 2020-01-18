package com.tim.delayschedule.server.storage.mapper;

import com.tim.delayschedule.server.core.model.SimpleScheduleEntry;
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
public class SimpleDelayTaskMapper implements RowMapper<SimpleScheduleEntry> {

    @Override
    public SimpleScheduleEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

        SimpleScheduleEntry simpleDelayTask = new SimpleScheduleEntry();

        simpleDelayTask.setId(rs.getString("id"));
        simpleDelayTask.setSlotId(rs.getInt("slot_id"));
        simpleDelayTask.setScheduleTime(rs.getLong("schedule_time"));

        return simpleDelayTask;
    }
}
