package com.tim.delayschedule.server.core.coordinator;

/**
 * @author wangkun
 */
@FunctionalInterface
public interface WatchEventCallBack {
    /**
     * 回调
     * @param event
     */
    void callback(WatchEvent event);
}
