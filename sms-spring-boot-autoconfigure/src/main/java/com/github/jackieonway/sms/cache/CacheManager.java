/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.cache;

import com.github.jackieonway.sms.entity.cache.Cache;
import com.github.jackieonway.sms.entity.cache.CacheBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jackie
 * @version $id: CacheManager.java v 0.1 2020-07-17 16:45 Jackie Exp $$
 */
public class CacheManager {

    private CacheManager() {
    }

    private static final ConcurrentHashMap<Object, Cache> CACHE_POOL = new ConcurrentHashMap<>(512);

    public static Cache putCacheIfNotFound1(@NotNull Object key, @NotNull Cache cache) {
        if (isExistKey(key)){
            throw new IllegalArgumentException(String.format("cache key [%s] is exits in cache", key));
        }
        cache.setLastModifyTime(System.currentTimeMillis());
        CACHE_POOL.put(key, cache);
        return cache;
    }

    public static void putCacheIfNotFound(@NotNull Object key, @NotNull Cache cache) {
        putCacheIfNotFound1(key, cache);
    }

    public static void putCacheIfNotFound(@NotNull Object key, @NotNull Object data, @NotNull int timeout){
        Cache cache = CacheBuilder.builder().data(data).timeout(timeout).build();
        putCacheIfNotFound1(key,cache);
    }

    public static boolean isExistKey(@NotNull Object key){
        return CACHE_POOL.containsKey(key);
    }

    public static boolean isNotExistKey(@NotNull Object key){
        return !isExistKey(key);
    }

    public static Cache put1(@NotNull Object key, @NotNull Cache cache) {
        cache.setLastModifyTime(System.currentTimeMillis());
        CACHE_POOL.put(key, cache);
        return cache;
    }

    public static void put(@NotNull Object key, @NotNull Cache cache) {
        put1(key, cache);
    }

    public static void put(@NotNull Object key, @NotNull Object data, @NotNull int timeout){
        Cache cache = CacheBuilder.builder().data(data).timeout(timeout).build();
        put(key,cache);
    }

    public static Cache get(@NotNull Object key){
        if (isTimeout(key)){
            remove(key);
            return null;
        }
        return CACHE_POOL.get(key);
    }

    public static void remove(@NotNull Object key){
        if (isExistKey(key)){
            CACHE_POOL.remove(key);
        }
    }

    public static boolean isTimeout(@NotNull Object key){
        if (isNotExistKey(key)){
            return Boolean.TRUE;
        }
        Cache cache = CACHE_POOL.get(key);
        if (Objects.isNull(cache)){
            return Boolean.TRUE;
        }
        int timeout = cache.getTimeout();
        long lastModifyTime = cache.getLastModifyTime();
        boolean flag = timeout <= 0 || System.currentTimeMillis() - lastModifyTime < timeout;
        return !flag;
    }

    public static Set<Object> getAllKeys(){
        return CACHE_POOL.keySet();
    }

    public static ConcurrentMap<Object, Cache> getAllCache(){
        return CACHE_POOL;
    }

    public static void putAll(ConcurrentMap<String, Cache> cacheHashMap){
        if (CollectionUtils.isEmpty(cacheHashMap)){
            return;
        }
        CACHE_POOL.putAll(cacheHashMap);
    }

    public static int size(){
        return CACHE_POOL.size();
    }
}
