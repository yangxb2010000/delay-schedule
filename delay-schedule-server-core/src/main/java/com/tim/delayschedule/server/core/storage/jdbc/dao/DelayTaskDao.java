package com.tim.delayschedule.server.core.storage.jdbc.dao;

import com.tim.delayschedule.server.core.constant.TaskDaoResult;
import com.tim.delayschedule.server.core.constant.TaskStatus;
import com.tim.delayschedule.server.core.model.KeyValuePair;
import com.tim.delayschedule.server.core.model.ScheduleEntry;
import com.tim.delayschedule.server.core.storage.jdbc.model.SimpleScheduleEntryDb;

import java.util.List;

/**
 * 功能描述 : DelayTask持久化接口
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-16 13:36
 */
public interface DelayTaskDao {
    /**
     * 根据id查询DelayTask
     * @Param : [id] DelayTask 唯一id值
     * @Return : com.tim.delayschedule.server.model.DelayTask
     */
    ScheduleEntry select(String id);

    /**
     * 根据slotIds和scheduleTime分页查询
     * @Param : [slotIds, scheduleTime, pageSize, cursor]
     * @Return : java.util.List<com.tim.delayschedule.core.model.DelayTask>
     */
    List<SimpleScheduleEntryDb> selectBySlotIdWithScheduleTime(List<Integer> slotIds, long cursor, long scheduleTime);

    /**
     * 根据id删除DelayTask
     * @Param : [id] DelayTask 唯一id值
     * @Return : com.tim.delayschedule.server.model.DelayTask
     */
    TaskDaoResult delete(String id);

    /**
     * 插入DelayTask
     * @Param : [delayTask]
     * @Return : com.tim.delayschedule.server.model.DelayTask
     */
    TaskDaoResult insert(ScheduleEntry scheduleEntry);

    /**
     * 批量插入DelayTask
     * @Param : [delayTask]
     * @Return : com.tim.delayschedule.server.constant.TaskDaoResult
     */
    TaskDaoResult insertBatch(List<ScheduleEntry> scheduleEntry);

    /**
     * 根据id修改DelayTask状态
     * @Param : [id]
     * @Return : com.tim.delayschedule.server.model.DelayTask
     */
    TaskDaoResult updateStatusById(String id, TaskStatus taskStatus);

    /**
     * 根据id批量修改DelayTask状态
     * @Param : [id, taskStatus]
     * @Return : com.tim.delayschedule.server.constant.TaskDaoResult
     */
    TaskDaoResult updateStatusByIdBatch(List<KeyValuePair<String, TaskStatus>> taskIdAndStatus);
}
