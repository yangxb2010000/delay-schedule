package com.tim.delayschedule.core.sharding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
