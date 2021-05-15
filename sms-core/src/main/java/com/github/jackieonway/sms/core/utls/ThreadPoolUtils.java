package com.github.jackieonway.sms.core.utls;

import com.github.jackieonway.sms.core.cache.CacheManager;
import com.github.jackieonway.sms.core.entity.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 *
 * @author bing_huang
 * @since 1.0.0
 */
public class ThreadPoolUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtils.class);
    private static final int CORE_POOL_SIZE = (int) (Runtime.getRuntime().availableProcessors() / 0.1);

    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE << 1;

    private static final int SCHEDULER_POOL_SIZE = 5;

    private static final String SERIALIZE_FILE_NAME = "serializeCache";

    public static AsyncTaskExecutor smsAsyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("sms-thread-");
        asyncTaskExecutor.setQueueCapacity(CORE_POOL_SIZE << 2);
        asyncTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    public static ThreadPoolTaskScheduler smsThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(SCHEDULER_POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("sms-schedule-");
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.schedule(ThreadPoolUtils::cleanCache, new CronTrigger("0 0/5 * * * ?"));
        return threadPoolTaskScheduler;
    }

    private static void cleanCache() {
        Set<Object> allKeys = CacheManager.getAllKeys();
        if (CollectionUtils.isEmpty(allKeys)) {
            return;
        }
        allKeys.forEach(key -> {
            if (CacheManager.isTimeout(key)) {
                CacheManager.remove(key);
            }
        });
        serializeToFile();
    }


    private static void serializeToFile() {
        ObjectOutputStream oos = null;
        try {
            String property = System.getProperty("java.io.tmpdir");
            String path = property + File.separator + SERIALIZE_FILE_NAME;
            oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
            oos.writeObject(CacheManager.getAllCache());
        } catch (IOException e) {
            LOGGER.error("serialaze cache error, reason: [{}]", e.getMessage());
        } finally {
            if (Objects.nonNull(oos)) {
                try {
                    oos.close();
                } catch (IOException e) {
                    LOGGER.error("serialaze cache error, reason: [{}]", e.getMessage());
                }
            }
        }

    }

    public static void deserialazeFromFile() {
        ObjectInputStream ois = null;
        try {
            String property = System.getProperty("java.io.tmpdir");
            String path = property + File.separator + SERIALIZE_FILE_NAME;
            ois = new ObjectInputStream(new FileInputStream(new File(path)));
            ConcurrentMap<String, Cache> cacheHashMap = (ConcurrentMap<String, Cache>) ois.readObject();
            CacheManager.putAll(cacheHashMap);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("deserialaze cache error, reason: [{}]", e.getMessage());
        } finally {
            if (Objects.nonNull(ois)) {
                try {
                    ois.close();
                } catch (IOException e) {
                    LOGGER.error("deserialaze cache error, reason: [{}]", e.getMessage());
                }
            }
        }
    }
}
