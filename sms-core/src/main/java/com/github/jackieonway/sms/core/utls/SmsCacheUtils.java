/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.core.utls;

import com.github.jackieonway.sms.core.cache.CacheManager;
import com.github.jackieonway.sms.core.entity.BaseRequest;
import com.github.jackieonway.sms.core.entity.Limit;
import com.github.jackieonway.sms.core.entity.SmsProperties;
import com.github.jackieonway.sms.core.entity.cache.Cache;
import com.github.jackieonway.sms.core.entity.cache.CacheBuilder;
import com.github.jackieonway.sms.core.exception.SmsException;

import java.util.Objects;

/**
 * @author Jackie
 * @version $id: SmsCacheUtils.java v 0.0.3 2020-07-18 10:43 Jackie Exp $$
 */
public class SmsCacheUtils {

    private SmsCacheUtils() {
    }

    /**
     * Get the timeout from the configuration
     *
     * @param limit configuration
     * @return timeout
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static int getTimeout(Limit limit) {
        return limit.getLimitTime();
    }

    /**
     * Get the enable cache config from the configuration
     *
     * @param limit configuration
     * @return enable cache config
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static boolean enableCache(Limit limit) {
        return limit.isEnable();
    }

    /**
     * put request into  cache
     *
     * @param phoneNumber phone number cache key
     * @param baseRequest cache value
     * @param timeout     timeout
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static void putCache(String phoneNumber, BaseRequest baseRequest, int timeout) {
        if (CacheManager.isExistKey(phoneNumber) && CacheManager.isTimeout(phoneNumber)) {
            CacheManager.remove(phoneNumber);
            CacheManager.putCacheIfNotFound(phoneNumber, baseRequest, timeout);
            return;
        }
        Cache cache = CacheManager.get(phoneNumber);
        if (Objects.nonNull(cache)) {
            CacheManager.put(phoneNumber, cache);
            return;
        }
        CacheManager.put(phoneNumber,
                CacheBuilder.builder().data(baseRequest).timeout(timeout).build());
    }

    /**
     * put request into  cache
     *
     * @param phoneNumber phone number cache key
     * @param baseRequest cache value
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static void putCache(String phoneNumber, BaseRequest baseRequest) {
        putCache(phoneNumber, baseRequest, 0);
    }

    /**
     * get cache value from cache pool
     *
     * @param phoneNumber cache key
     * @return cache value
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static Object getCache(String phoneNumber) {
        Cache cache = CacheManager.get(phoneNumber);
        if (Objects.isNull(cache)) {
            return null;
        }
        return cache.getData();
    }

    /**
     * Store SMS content in the buffer pool
     *
     * @param phoneNumber   phone number
     * @param baseRequest   sms content
     * @param smsProperties global sms configuration
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    public static void cacheSms(String phoneNumber, BaseRequest baseRequest, SmsProperties smsProperties) {

        if (CacheManager.isMax()) {
            switch (smsProperties.getCleanStrategy()) {
                case LESS_TIME:
                    CacheManager.removeLeastTimeRemaining();
                    break;
                case FIRST:
                default:
                    CacheManager.removeFirst();
            }

        }
        boolean baseRequestEnableCache = enableCache(baseRequest.getLimit());
        boolean globalEnableCache = enableCache(smsProperties.getLimit());
        if (!baseRequestEnableCache && !globalEnableCache) {
            return;
        }
        if (baseRequestEnableCache) {
            putCache(phoneNumber, baseRequest, baseRequest.getLimit());
            return;
        }
        putCache(phoneNumber, baseRequest, smsProperties.getLimit());
    }

    /**
     * put request into  cache
     *
     * @param phoneNumber phone number cache key
     * @param baseRequest cache value
     * @param limit       limit configuration
     * @author Jackie
     * @see SmsCacheUtils
     * @since 0.0.3
     */
    private static void putCache(String phoneNumber, BaseRequest baseRequest, Limit limit) {
        if (Objects.nonNull(getCache(phoneNumber))) {
            throw new SmsException(String.format("the phoneNumber [%s] has sent sms", phoneNumber));
        }
        int timeout = getTimeout(limit);
        putCache(phoneNumber, baseRequest, timeout);
    }
}
