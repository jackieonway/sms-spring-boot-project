package com.github.jackieonway.sms.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SmsType;
import com.github.jackieonway.sms.service.AliSmsService;
import com.github.jackieonway.sms.service.SmsService;
import com.github.jackieonway.sms.service.TencentSmsService;
import com.github.jackieonway.sms.service.UcpassSmsService;
import com.github.jackieonway.sms.ucpass.client.JsonReqClient;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;

/**
 * @author Jackie
 * @version \$id: SmsAutoConfigure.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfigure {

    @Configuration
    @ConditionalOnClass({SmsSingleSender.class,SmsMultiSender.class})
    public static class TentcentSmsServiceConfiguration{

        @Bean
        public SmsService tencentSmsService(SmsProperties smsProperties){
            if(SmsType.TENCENT.equals(smsProperties.getSmsType())){
                return new TencentSmsService(
                        new SmsSingleSender(
                                Integer.parseInt(smsProperties.getAppid()),
                                smsProperties.getSecurityKey()),
                        new SmsMultiSender(
                                Integer.parseInt(smsProperties.getAppid()),
                                smsProperties.getSecurityKey()),
                        smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({IAcsClient.class})
    public static class AliSmsServiceConfiguration {

        @Bean
        public SmsService aliSmsService(SmsProperties smsProperties){
            if (SmsType.ALI.equals(smsProperties.getSmsType())){
                DefaultProfile defaultProfile = DefaultProfile.getProfile(smsProperties.getRegionId(),
                        smsProperties.getAccessKey(),smsProperties.getSecurityKey());
                return new AliSmsService(
                        new DefaultAcsClient(defaultProfile),
                        smsProperties);
            }
            return null;
        }
    }

    @Configuration
    @ConditionalOnClass({JsonReqClient.class})
    public static class UCPassSmsServiceConfiguration{

        @Bean
        public SmsService ucpassSmsService(SmsProperties smsProperties){
            if (SmsType.UCPASS.equals(smsProperties.getSmsType())){
                return new UcpassSmsService(
                    new JsonReqClient(smsProperties),
                    smsProperties);
            }
            return null;
        }
    }
}
