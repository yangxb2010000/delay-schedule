package com.tim.delayschedule.server.core.slotsharding;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 基于slot的方式实现的分片机制 slot的机制类似于redis cluster的slot分片方式
 * TODO: 添加watch ServiceInstance与Slot对应关系变化的事件逻辑
 */
public interface SlotSharding {
    /**
     * 默认slot的总数
     */
    int DEFAULT_SLOT_COUNT = 128;

    /**
     * 释放指定slot，即当前实例不再覆盖该slotId
     *
     * @param slotId
     * @param slotChangeVersion slotChange的version，如果传入的slotChangeVersion小于或者等于当前实例的slotChangeVersion，则忽略该请求
     */
    void releaseSlot(int slotId, int slotChangeVersion);

    /**
     * 查看当前实例是否覆盖指定slotId
     *
     * @param slotId slotId
     * @return
     */
    boolean shouldHandle(int slotId);

    /**
     * 获取当前实例分配的SlotId list
     *
     * @return
     */
    List<Integer> getHandledSlots();

    /**
     * 注册当前实例覆盖的slotId list变化的事件, 当前方法被调用时，如果HandledSlot不为空，就直接触发一次事件。
     *
     * @return
     */
    void registerHandledSlotChangeListener(Consumer<List<Integer>> slotChangeListener);

    /**
     * 注册ScheduleServer实例与Slot对应关系变化的事件, 当前方法被调用时，如果Server2Slot不为空，就直接触发一次事件。
     *
     * @return
     */
    void registerServer2SlotChangeListener(Consumer<Map<ServiceInstance, List<Integer>>> slotChangeListener);

    @Data
    @ToString
    @EqualsAndHashCode
    class ServiceInstance {
        private String ip;
        private String port;
    }
}
