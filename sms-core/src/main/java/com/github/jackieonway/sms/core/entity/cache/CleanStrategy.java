/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.core.entity.cache;

/**
 * @author Jackie
 * @version $id: CleanStrategy.java v 0.0.3 2020-08-17 8:58 Jackie Exp $$
 */
public enum  CleanStrategy {

    /**
     * remove the first cache
     */
    FIRST,
    /**
     * remove the less time‘s cache
     */
    LESS_TIME
}
