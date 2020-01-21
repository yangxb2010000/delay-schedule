package com.tim.delayschedule.server.core.coordinator;

/**
 * @author wangkun
 */
public enum NodeEventType {
    /**
     * 无
     */
    None,
    /**
     * 新建节点
     */
    NodeCreated,
    /**
     * 节点删除
     */
    NodeDeleted,
    /**
     * 节点修改
     */
    NodeDataChanged,
    /**
     * 自节点变化
     */
    NodeChildrenChanged;
}
