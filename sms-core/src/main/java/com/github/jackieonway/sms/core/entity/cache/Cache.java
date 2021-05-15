/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.core.entity.cache;

import java.io.Serializable;

/**
 * @author Jackie
 * @version $id: Cache.java v 0.0.3 2020-07-17 16:00 Jackie Exp $$
 */
public class Cache implements Serializable {

    private static final long serialVersionUID = 7107322407245660371L;

    private Object data;

    private int timeout;

    private long lastModifyTime;

    public Object getData() {
        return data;
    }

    public Cache setData(Object data) {
        this.data = data;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public Cache setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public Cache setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public Cache(Object data, int timeout) {
        this.data = data;
        this.timeout = timeout;
    }
}
