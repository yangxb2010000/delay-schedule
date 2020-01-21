package com.tim.delayschedule.server.core.storage.jdbc.mapper;

import com.tim.delayschedule.server.core.storage.jdbc.model.SimpleScheduleEntryDb;
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
public class SimpleScheduleEntryDbMapper implements RowMapper<SimpleScheduleEntryDb> {

    @Override
    public SimpleScheduleEntryDb mapRow(ResultSet rs, int rowNum) throws SQLException {

        SimpleScheduleEntryDb simpleScheduleEntryDb = new SimpleScheduleEntryDb();

        simpleScheduleEntryDb.setRecordId(rs.getInt("record_id"));
        simpleScheduleEntryDb.setId(rs.getString("id"));
        simpleScheduleEntryDb.setSlotId(rs.getInt("slot_id"));
        simpleScheduleEntryDb.setNextScheduleTime(rs.getLong("schedule_time"));

        return simpleScheduleEntryDb;
    }
}
