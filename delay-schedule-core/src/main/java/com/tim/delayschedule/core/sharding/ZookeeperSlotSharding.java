package com.tim.delayschedule.core.sharding;

import java.util.Map;

/**
 * 基于Zookeeper实现的SlotSharding策略， slot的具体分配策略存储在Zookeeper中
 */
public class ZookeeperSlotSharding implements SlotSharding {
    @Override
    public boolean shouldHandle(int slotId) {
        return false;
    }

    @Override
    public SlotRange getHandledSlots() {
        return null;
    }

    @Override
    public Map<ServiceInstance, SlotRange> loadServiceInstanceList() {
        return null;
    }
}
