/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.sms.utls;

import com.github.jackieonway.sms.cache.CacheManager;
import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.Limit;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.cache.Cache;
import com.github.jackieonway.sms.entity.cache.CacheBuilder;
import com.github.jackieonway.sms.exception.SmsException;

import java.util.Objects;

/**
 * @author Jackie
 * @version $id: SmsCacheUtils.java v 0.1 2020-07-18 10:43 Jackie Exp $$
 */
public class SmsCacheUtils {

    private SmsCacheUtils() {
    }

    public static int getTimeout(SmsProperties smsProperties){
        Limit limit = smsProperties.getLimit();
        return limit.getLimitTime();
    }

    public static boolean enableCache(SmsProperties smsProperties){
        return smsProperties.getLimit().isEnable();
    }

    public static void putCache(String phoneNumber, BaseRequest baseRequest, int timeout){
        if (CacheManager.isExistKey(phoneNumber) && CacheManager.isTimeout(phoneNumber)){
            CacheManager.putCacheIfNotFound(phoneNumber, baseRequest, timeout);
        }
        Cache cache = CacheManager.get(phoneNumber);
        if (Objects.nonNull(cache)) {
            cache.setLastModifyTime(System.currentTimeMillis());
            CacheManager.put(phoneNumber, cache);
        }
        CacheManager.put(phoneNumber,
                CacheBuilder.builder().data(baseRequest).timeout(timeout).build());
    }

    public static void putCache(String phoneNumber, BaseRequest baseRequest){
        putCache(phoneNumber, baseRequest , 0);
    }

    public static Object getCache(String phoneNumber){
        Cache cache = CacheManager.get(phoneNumber);
        if (Objects.isNull(cache)){
            return null;
        }
        return cache.getData();
    }

    public static void cacheSms(String phoneNumber, BaseRequest baseRequest, SmsProperties smsProperties){
        if (!enableCache(smsProperties)){
           return;
        }
        if (Objects.nonNull(getCache(phoneNumber))){
            throw new SmsException(String.format("the phoneNumber [%s] has sent sms", phoneNumber));
        }
        int timeout = getTimeout(smsProperties);
        putCache(phoneNumber, baseRequest, timeout);
    }
}
