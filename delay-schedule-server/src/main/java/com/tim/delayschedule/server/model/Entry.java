package com.tim.delayschedule.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-17 09:21
 */
@Data
@AllArgsConstructor
public class Entry<K, V> {
    private K key;

    private V value;
}
