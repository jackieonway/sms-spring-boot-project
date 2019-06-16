package com.github.jackieonway.sms.autoconfigure;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;
import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SmsType;
import com.github.jackieonway.sms.service.AliSmsService;
import com.github.jackieonway.sms.service.SmsService;
import com.github.jackieonway.sms.service.TencentSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jackie
 * @version \$id: PengzuSmsAutoConfigure.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
@Configuration
@ConditionalOnBean(annotation = EnabledSmsAutoConfiguration.class)
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfigure {

    private SmsProperties smsProperties;
    private ApplicationContext applicationContext;
    public SmsAutoConfigure(SmsProperties smsProperties, ApplicationContext applicationContext) {
        this.smsProperties = smsProperties;
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean(SmsSingleSender.class)
    public SmsSingleSender createSmsSingleSender(){
        if (SmsType.TENTCENT.equals(smsProperties.getSmsType())){
            return new SmsSingleSender(
                    Integer.parseInt(smsProperties.getAppid()),
                    smsProperties.getSecurityKey());
        }
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(SmsMultiSender.class)
    public SmsMultiSender createSmsMultiSender(){
        if (SmsType.TENTCENT.equals(smsProperties.getSmsType())){
            return new SmsMultiSender(
                    Integer.parseInt(smsProperties.getAppid()),
                    smsProperties.getSecurityKey());
        }
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(IAcsClient.class)
    public IAcsClient createIAcsClient(){
        if (SmsType.ALI.equals(smsProperties.getSmsType())){
            DefaultProfile defaultProfile = DefaultProfile.getProfile(smsProperties.getRegionId(),
                    smsProperties.getAccessKey(),smsProperties.getSecurityKey());
            return new DefaultAcsClient(defaultProfile);
        }
        return null;
    }

    @Bean
    public SmsService smsService(){
        return getSmsService();
    }

    private SmsService getSmsService(){
        if(SmsType.ALI.equals(smsProperties.getSmsType())){
            return new AliSmsService(
                    applicationContext.getBean(IAcsClient.class),
                    smsProperties);
        } else if(SmsType.TENTCENT.equals(smsProperties.getSmsType())){
            return new TencentSmsService(
                    applicationContext.getBean(SmsSingleSender.class),
                    applicationContext.getBean(SmsMultiSender.class),
                    smsProperties);
        }
        return null;
    }
}
