package com.example.demo.sms.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池的配置
 *
 * @author Jackie
 */
@EnableAsync
@Configuration
public class AsyncThreadExecutorConfig {

    private static final int MAX_POOL_SIZE = 50;

    private static final int CORE_POOL_SIZE = 100;

    @Bean("asyncThreadExecutor")
    public AsyncTaskExecutor asyncThreadExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("async-thread-pool-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}