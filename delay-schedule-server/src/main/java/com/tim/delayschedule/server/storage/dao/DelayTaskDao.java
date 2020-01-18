package com.tim.delayschedule.server.storage.dao;

import com.tim.delayschedule.server.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.core.model.DelayTask;
import com.tim.delayschedule.server.model.Entry;

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
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    DelayTask select(String id);

    /**
     * 根据slotIds和scheduleTime分页查询
     * @Param : [slotIds, scheduleTime, pageSize, cursor]
     * @Return : java.util.List<com.tim.delayschedule.core.model.DelayTask>
     */
    List<DelayTask> selectBySlotIdWithScheduleTime(List<Integer> slotIds, long scheduleTime, int pageSize, int cursor);

    /**
     * 根据id删除DelayTask
     * @Param : [id] DelayTask 唯一id值
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult delete(String id);

    /**
     * 插入DelayTask
     * @Param : [delayTask]
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult insert(DelayTask delayTask);

    /**
     * 批量插入DelayTask
     * @Param : [delayTask]
     * @Return : com.tim.delayschedule.server.constant.TaskDaoResult
     */
    TaskDaoResult insertBatch(List<DelayTask> delayTask);

    /**
     * 根据id修改DelayTask状态
     * @Param : [id]
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult updateStatusById(String id, TaskStatus taskStatus);

    /**
     * 根据id批量修改DelayTask状态
     * @Param : [id, taskStatus]
     * @Return : com.tim.delayschedule.server.constant.TaskDaoResult
     */
    TaskDaoResult updateStatusByIdBatch(List<Entry<String, TaskStatus>> taskIdAndStatus);
}
