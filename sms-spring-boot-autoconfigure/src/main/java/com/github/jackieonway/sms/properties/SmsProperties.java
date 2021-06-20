package com.github.jackieonway.sms.properties;

import com.github.jackieonway.sms.core.entity.Limit;
import com.github.jackieonway.sms.core.entity.SmsTypeEnum;
import com.github.jackieonway.sms.core.entity.cache.CleanStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * @author Jackie
 * @version \$id: SmsProperties.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
@ConfigurationProperties(prefix = "spring.jackieonway.sms")
public class SmsProperties {

    @NestedConfigurationProperty
    private SmsTypeEnum smsType = SmsTypeEnum.ALI;

    /**
     * sms appid
     */
    private String appid;

    /**
     * domain
     */
    private String domain;
    /**
     * version
     */
    private String version;
    /**
     * application access  key
     */
    private String accessKey;

    /**
     *  application security  key
     */
    private String securityKey;

    /**
     * sms server region
     */
    private String region;

    /**
     * other params
     */
    private Map<String, String> params;

    /**
     *  limit param
     */
    @NestedConfigurationProperty
    private Limit limit = new Limit();

    private CleanStrategy cleanStrategy= CleanStrategy.FIRST;

    public SmsTypeEnum getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTypeEnum smsType) {
        this.smsType = smsType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public CleanStrategy getCleanStrategy() {
        return cleanStrategy;
    }

    public void setCleanStrategy(CleanStrategy cleanStrategy) {
        this.cleanStrategy = cleanStrategy;
    }

    @Override
    public String toString() {
        return "SmsProperties{" +
                "smsType=" + smsType +
                ", appid='" + appid + '\'' +
                ", domain='" + domain + '\'' +
                ", version='" + version + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", region='" + region + '\'' +
                ", params=" + params +
                ", limit=" + limit +
                ", cleanStrategy=" + cleanStrategy +
                '}';
    }
}
