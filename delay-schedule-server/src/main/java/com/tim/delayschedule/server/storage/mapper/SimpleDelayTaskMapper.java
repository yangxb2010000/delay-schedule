package com.tim.delayschedule.server.storage.mapper;

import com.tim.delayschedule.server.model.SimpleDelayTask;
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
public class SimpleDelayTaskMapper implements RowMapper<SimpleDelayTask> {

    @Override
    public SimpleDelayTask mapRow(ResultSet rs, int rowNum) throws SQLException {

        SimpleDelayTask simpleDelayTask = new SimpleDelayTask();

        simpleDelayTask.setId(rs.getString("id"));
        simpleDelayTask.setSlotId(rs.getInt("slot_id"));
        simpleDelayTask.setScheduleTime(rs.getLong("schedule_time"));

        return simpleDelayTask;
    }
}
