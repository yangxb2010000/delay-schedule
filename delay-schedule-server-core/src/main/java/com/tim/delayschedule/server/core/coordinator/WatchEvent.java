package com.tim.delayschedule.server.core.coordinator;

import lombok.Data;

/**
 * watch event
 * @author wangkun
 */
@Data
public class WatchEvent {
    /**
     * event type
     */
    private NodeEventType nodeEventType;

    /**
     * node watched
     */
    private Node eventNodeList;
}
