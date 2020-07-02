package com.github.jackieonway.sms.autoconfigure;

import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SmsTypeEnum;
import com.github.jackieonway.sms.service.AliSmsService;
import com.github.jackieonway.sms.service.SmsService;
import com.github.jackieonway.sms.service.TencentSmsService;
import com.github.jackieonway.sms.service.UcpassSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jackie
 * @version \$id: SmsAutoConfigure.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfigure {

    @Configuration
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
    public static class UCPassSmsServiceConfiguration {

        @Bean
        public SmsService ucpassSmsService(SmsProperties smsProperties) {
            if (SmsTypeEnum.UCPASS.equals(smsProperties.getSmsType())) {
                return new UcpassSmsService(smsProperties);
            }
            return null;
        }
    }
}
