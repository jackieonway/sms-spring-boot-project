package com.github.jackieonway.sms.entity;

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
     * 短信应用商服务地址
     */
    private String appid;

    /**
     * 域名地址
     */
    private String domain;
    /**
     * 版本
     */
    private String version;
    /**
     * 短信服务商应用公钥
     */
    private String accessKey;

    /**
     * 短信服务商应用私钥
     */
    private String securityKey;

    /**
     * 区域
     */
    private String region;

    /**
     * 其余参数
     */
    private Map<String, String> maps;

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

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
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
                ", maps=" + maps +
                '}';
    }
}
