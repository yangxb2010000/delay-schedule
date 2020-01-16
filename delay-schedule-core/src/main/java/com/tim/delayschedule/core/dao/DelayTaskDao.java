package com.tim.delayschedule.core.dao;

import com.tim.delayschedule.core.constant.TaskDaoResult;
import com.tim.delayschedule.core.constant.TaskStatus;
import com.tim.delayschedule.core.model.DelayTask;

/**
 * 功能描述 : TODO
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
     * 根据id删除DelayTask
     * @Param : [id] DelayTask 唯一id值
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult delete(String id);

    /**
     * 创建DelayTask
     * @Param : [delayTask]
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult insert(DelayTask delayTask);

    /**
     * 根据id修改DelayTask状态
     * @Param : [id]
     * @Return : com.tim.delayschedule.core.model.DelayTask
     */
    TaskDaoResult updateStatusById(String id, TaskStatus taskStatus);
}
