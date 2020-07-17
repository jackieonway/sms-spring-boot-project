package com.github.jackieonway.sms.autoconfigure;

import com.aliyuncs.IAcsClient;
import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SmsTypeEnum;
import com.github.jackieonway.sms.service.*;
import com.github.jackieonway.sms.submail.client.SubMailClient;
import com.github.jackieonway.sms.ucpass.client.JsonReqClient;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Jackie
 * @version \$id: SmsAutoConfigure.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */

@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfigure {

    @Configuration
    @ConditionalOnClass({SmsClient.class})
    public static class TentcentSmsServiceConfiguration {

        @Bean
        public SmsService tencentSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.TENCENT.equals(smsProperties.getSmsType())) {
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
                return new AliSmsService(smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({JsonReqClient.class})
    public static class UCPassSmsServiceConfiguration {

        @Bean
        public SmsService ucpassSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.UCPASS.equals(smsProperties.getSmsType())) {
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
                return new SubMailSmsServiceImpl(properties);
            }
            return null;
        }
    }

    private static final int MAX_POOL_SIZE = 60;

    private static final int CORE_POOL_SIZE = 30;

    @Bean
    public AsyncTaskExecutor executorService(){
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("sms-thread-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
