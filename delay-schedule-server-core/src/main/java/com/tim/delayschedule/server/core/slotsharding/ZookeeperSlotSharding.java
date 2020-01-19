package com.tim.delayschedule.server.core.slotsharding;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 基于Zookeeper实现的SlotSharding策略， slot的具体分配策略存储在Zookeeper中
 */
public class ZookeeperSlotSharding implements SlotSharding {
    @Override
    public void releaseSlot(int slotId, int slotChangeVersion) {

    }

    @Override
    public boolean shouldHandle(int slotId) {
        return false;
    }

    @Override
    public List<Integer> getHandledSlots() {
        return null;
    }

    @Override
    public void registerHandledSlotChangeListener(Consumer<List<Integer>> slotChangeListener) {

    }

    @Override
    public void registerServer2SlotChangeListener(Consumer<Map<ServiceInstance, List<Integer>>> slotChangeListener) {

    }
}
