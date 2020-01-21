package com.tim.delayschedule.server.core.coordinator;

/**
 * @author wangkun
 */
public interface CoordinateClient {

    /**
     * watch 某个节点，回调节点变化信息
     * @param callBack
     */
    void watch(WatchEventCallBack callBack);

    /**
     * 获取某个节点的信息
     * @param path
     * @return
     */
    Node get(String path);

    /**
     * 创建一个节点，如果存在则修改
     * @param path
     * @param value
     * @return
     */
    boolean createNode(String path, String value);

    /**
     * 注册某个服务，需要自动过期或失效
     * @param serverName
     */
    void register(String serverName);

    /**
     * 注销某一个服务
     * @param serverName
     */
    void unRegister(String serverName);

}
