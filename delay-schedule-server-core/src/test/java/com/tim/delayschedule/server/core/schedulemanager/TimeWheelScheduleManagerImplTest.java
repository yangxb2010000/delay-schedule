package com.tim.delayschedule.server.core.schedulemanager;

import com.tim.delayschedule.server.core.slotsharding.SlotSharding;
import com.tim.delayschedule.server.core.storage.DelayTaskStorage;
import com.tim.delayschedule.utils.DataSourceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;

import static org.mockito.Mockito.spy;

/**
 * @author xiaobing
 * @date 2020/1/16
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeWheelScheduleManagerImplTest {


    @Test
    public void push() {
    }

    @Test
    public void setSlotRange() {
        SlotSharding slotSharding = spy(SlotSharding.class);
        DelayTaskStorage delayTaskStorage = spy(DelayTaskStorage.class);
        DataSource dataSource = DataSourceUtils.initDataSource();

        TimeWheelScheduleManagerImpl scheduleManager = new TimeWheelScheduleManagerImpl(dataSource);
    }
}