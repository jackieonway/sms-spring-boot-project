/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.entity.cache;

/**
 * @author Jackie
 * @version $id: CacheBuilder.java v 0.1 2020-07-17 16:02 Jackie Exp $$
 */
public class CacheBuilder {

    private Object data;

    private int timeout = 0;

    public static CacheBuilder builder(){
        return new CacheBuilder();
    }

    public CacheBuilder data(Object data){
        this.data = data;
        return this;
    }

    public CacheBuilder timeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public Cache build(){
        Cache cache = new Cache(this.data, this.timeout);
        cache.setLastModifyTime(System.currentTimeMillis());
        return cache;
    }

}
