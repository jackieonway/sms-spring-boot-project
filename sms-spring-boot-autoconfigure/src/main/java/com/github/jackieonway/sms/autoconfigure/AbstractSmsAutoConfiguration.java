package com.github.jackieonway.sms.autoconfigure;

import com.github.jackieonway.sms.core.entity.SmsProperties;

/**
 * @author bing_huang
 * @since 1.0.0
 */
public abstract class AbstractSmsAutoConfiguration {
    protected SmsProperties config(com.github.jackieonway.sms.properties.SmsProperties smsProperties, SmsProperties properties) {
        properties.setSmsType(smsProperties.getSmsType());
        properties.setAppid(smsProperties.getAppid());
        properties.setDomain(smsProperties.getDomain());
        properties.setVersion(smsProperties.getVersion());
        properties.setAccessKey(smsProperties.getAccessKey());
        properties.setSecurityKey(smsProperties.getSecurityKey());
        properties.setRegion(smsProperties.getRegion());
        properties.setParams(smsProperties.getParams());
        properties.setLimit(smsProperties.getLimit());
        properties.setCleanStrategy(smsProperties.getCleanStrategy());
        return properties;
    }
}
