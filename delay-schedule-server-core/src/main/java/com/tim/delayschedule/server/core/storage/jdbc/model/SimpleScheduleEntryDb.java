package com.tim.delayschedule.server.core.storage.jdbc.model;

import com.tim.delayschedule.server.core.model.SimpleScheduleEntry;
import lombok.Data;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-19 00:55
 */
@Data
public class SimpleScheduleEntryDb extends SimpleScheduleEntry{

    private int recordId;

}
