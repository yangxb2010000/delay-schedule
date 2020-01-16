package com.tim.delayschedule.server.schedulemanager;

import com.tim.delayschedule.core.sharding.SlotSharding;
import com.tim.delayschedule.server.storage.DelayTaskStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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


        TimeWheelScheduleManagerImpl scheduleManager = new TimeWheelScheduleManagerImpl();
    }
}