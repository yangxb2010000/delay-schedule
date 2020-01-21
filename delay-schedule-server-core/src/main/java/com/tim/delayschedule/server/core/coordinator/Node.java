package com.tim.delayschedule.server.core.coordinator;

import lombok.Data;

/**
 * node of corrdinator
 * @author wangkun
 */
@Data
public class Node {
    /**
     * 路径
     */
    private String path;

    /**
     * 值
     */
    private String value;
}
