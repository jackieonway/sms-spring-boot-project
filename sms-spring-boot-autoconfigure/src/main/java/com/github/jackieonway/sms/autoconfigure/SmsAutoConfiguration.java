package com.github.jackieonway.sms.autoconfigure;

import com.aliyuncs.IAcsClient;
import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.core.entity.SmsTypeEnum;
import com.github.jackieonway.sms.core.service.AliSmsService;
import com.github.jackieonway.sms.core.service.SmsService;
import com.github.jackieonway.sms.core.service.SubMailSmsServiceImpl;
import com.github.jackieonway.sms.core.service.TencentSmsService;
import com.github.jackieonway.sms.core.service.UcpassSmsService;
import com.github.jackieonway.sms.core.utls.ThreadPoolUtils;
import com.github.jackieonway.sms.properties.SmsProperties;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Jackie
 * @version \$id: SmsAutoConfiguration.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */

@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration extends AbstractSmsAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsAutoConfiguration.class);

    @Configuration
    @ConditionalOnClass({SmsClient.class})
    public static class TentcentSmsServiceConfiguration extends AbstractSmsAutoConfiguration {

        @Bean
        public SmsService tencentSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.TENCENT.equals(smsProperties.getSmsType())) {
                ThreadPoolUtils.deserialazeFromFile();
                com.github.jackieonway.sms.core.entity.SmsProperties properties
                        = config(smsProperties, new com.github.jackieonway.sms.core.entity.SmsProperties());
                return new TencentSmsService(properties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({IAcsClient.class})
    public static class AliSmsServiceConfiguration extends AbstractSmsAutoConfiguration {

        @Bean
        public SmsService aliSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.ALI.equals(smsProperties.getSmsType())) {
                ThreadPoolUtils.deserialazeFromFile();
                com.github.jackieonway.sms.core.entity.SmsProperties properties
                        = config(smsProperties, new com.github.jackieonway.sms.core.entity.SmsProperties());
                return new AliSmsService(properties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({JsonReqClient.class})
    public static class UcPassSmsServiceConfiguration extends AbstractSmsAutoConfiguration {

        @Bean
        public SmsService ucpassSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.UCPASS.equals(smsProperties.getSmsType())) {
                ThreadPoolUtils.deserialazeFromFile();
                com.github.jackieonway.sms.core.entity.SmsProperties properties
                        = config(smsProperties, new com.github.jackieonway.sms.core.entity.SmsProperties());
                return new UcpassSmsService(properties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({SubMailClient.class})
    public static class SubMailServiceConfiguration extends AbstractSmsAutoConfiguration {
        @Bean
        public SmsService subMailService(SmsProperties properties) {
            if (SmsTypeEnum.SUBMAIL.equals(properties.getSmsType())) {
                ThreadPoolUtils.deserialazeFromFile();
                com.github.jackieonway.sms.core.entity.SmsProperties smsProperties
                        = config(properties, new com.github.jackieonway.sms.core.entity.SmsProperties());
                return new SubMailSmsServiceImpl(smsProperties);
            }
            return null;
        }
    }

    @Bean
    public AsyncTaskExecutor smsAsyncTaskExecutor() {
        return ThreadPoolUtils.smsAsyncTaskExecutor();
    }

    @Bean
    public ThreadPoolTaskScheduler smsThreadPoolTaskScheduler() {
        return ThreadPoolUtils.smsThreadPoolTaskScheduler();
    }
}
