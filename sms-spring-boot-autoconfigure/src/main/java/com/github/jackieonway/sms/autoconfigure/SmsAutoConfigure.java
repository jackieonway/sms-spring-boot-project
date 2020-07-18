package com.github.jackieonway.sms.autoconfigure;

import com.aliyuncs.IAcsClient;
import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.cache.CacheManager;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SmsTypeEnum;
import com.github.jackieonway.sms.entity.cache.Cache;
import com.github.jackieonway.sms.service.*;
import com.github.jackieonway.sms.submail.client.SubMailClient;
import com.github.jackieonway.sms.ucpass.client.JsonReqClient;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Objects;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jackie
 * @version \$id: SmsAutoConfigure.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */

@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfigure {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsAutoConfigure.class);

    @Configuration
    @ConditionalOnClass({SmsClient.class})
    public static class TentcentSmsServiceConfiguration {

        @Bean
        public SmsService tencentSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.TENCENT.equals(smsProperties.getSmsType())) {
                deserialazeFromFile();
                return new TencentSmsService(smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({IAcsClient.class})
    public static class AliSmsServiceConfiguration {

        @Bean
        public SmsService aliSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.ALI.equals(smsProperties.getSmsType())) {
                deserialazeFromFile();
                return new AliSmsService(smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({JsonReqClient.class})
    public static class UcPassSmsServiceConfiguration {

        @Bean
        public SmsService ucpassSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.UCPASS.equals(smsProperties.getSmsType())) {
                deserialazeFromFile();
                return new UcpassSmsService(smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({SubMailClient.class})
    public static class SubMailServiceConfiguration {
        @Bean
        public SmsService subMailService(SmsProperties properties) {
            if (SmsTypeEnum.SUBMAIL.equals(properties.getSmsType())) {
                deserialazeFromFile();
                return new SubMailSmsServiceImpl(properties);
            }
            return null;
        }
    }

    private static final int MAX_POOL_SIZE = 60;

    private static final int CORE_POOL_SIZE = 30;

    private static final int SCHEDULER_POOL_SIZE = 5;

    private static final String SERIALAZE_FILE_NAME = "serializeCache";

    @Bean
    public AsyncTaskExecutor executorService(){
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("sms-thread-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    @Bean
    public ThreadPoolTaskScheduler smsThradPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(SCHEDULER_POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("sms-schedule-");
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                SmsAutoConfigure.cleanCache();
            }
        }, new CronTrigger("0 0/5 * * * ?"));
        return threadPoolTaskScheduler;
    }

    private static void cleanCache(){
        Set<Object> allKeys = CacheManager.getAllKeys();
        if (CollectionUtils.isEmpty(allKeys)){
            return;
        }
        allKeys.forEach(key -> {
            if (CacheManager.isTimeout(key)){
                CacheManager.remove(key);
            }
        });
        serializeToFile();
    }


    private static void serializeToFile(){
        ObjectOutputStream oos = null;
        try {
            String property = System.getProperty("java.io.tmpdir");
            String path = property + File.separator + SERIALAZE_FILE_NAME;
            oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
            oos.writeObject(CacheManager.getAllCache());
        } catch (IOException e) {
            LOGGER.error("serialaze cache error, reason: [{}]",e.getMessage());
        }finally {
            if (Objects.nonNull(oos)){
                try {
                    oos.close();
                } catch (IOException e) {
                    LOGGER.error("serialaze cache error, reason: [{}]",e.getMessage());
                }
            }
        }

    }

    private static void deserialazeFromFile(){
        ObjectInputStream ois = null;
        try {
            String property = System.getProperty("java.io.tmpdir");
            String path = property + File.separator + SERIALAZE_FILE_NAME;
            ois = new ObjectInputStream(new FileInputStream(new File(path)));
            ConcurrentHashMap<String, Cache> cacheHashMap = (ConcurrentHashMap<String, Cache>)ois.readObject();
            CacheManager.putAll(cacheHashMap);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("deserialaze cache error, reason: [{}]",e.getMessage());
        }finally {
            if (Objects.nonNull(ois)){
                try {
                    ois.close();
                } catch (IOException e) {
                    LOGGER.error("deserialaze cache error, reason: [{}]",e.getMessage());
                }
            }
        }
    }
}
