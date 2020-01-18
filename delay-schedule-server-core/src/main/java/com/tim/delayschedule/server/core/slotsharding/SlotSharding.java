package com.tim.delayschedule.server.core.slotsharding;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 基于slot的方式实现的分片机制 slot的机制类似于redis cluster的slot分片方式
 * TODO: 添加watch ServiceInstance与Slot对应关系变化的事件逻辑
 */
public interface SlotSharding {
    /**
     * 查看当前实例是否覆盖制定slotId
     *
     * @param slotId slotId
     * @return
     */
    boolean shouldHandle(int slotId);

    /**
     * 获取当前实例需要覆盖的SlotId list
     *
     * @return
     */
    List<Integer> getHandledSlots();

    /**
     * 获取所有Slot与Service Instance的对应关系
     *
     * @return
     */
    Map<ServiceInstance, List<Integer>> loadServiceInstanceList();

    @Data
    @ToString
    @EqualsAndHashCode
    class ServiceInstance {
        private String ip;
        private String port;
    }
}
