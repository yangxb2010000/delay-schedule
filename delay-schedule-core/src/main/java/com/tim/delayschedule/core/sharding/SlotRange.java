package com.tim.delayschedule.core.sharding;

import lombok.Data;

@Data
public class SlotRange {
    /**
     * 覆盖的最小slotId
     */
    private int minSlotId;
    /**
     * 覆盖的最大slotId
     */
    private int maxSlotId;
}
