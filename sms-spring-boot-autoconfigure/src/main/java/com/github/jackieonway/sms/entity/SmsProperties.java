package com.github.jackieonway.sms.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jackie
 * @version \$id: SmsProperties.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
@ConfigurationProperties(prefix = "spring.jackieonway.sms")
public class SmsProperties {

    private SmsType smsType = SmsType.ALI;

    /**
     * 短信应用商服务地址
     */
    private String appid;

    /**
     * 短信服务商应用公钥
     */
    private String accessKey;

    /**
     *  短信服务商应用私钥
     */
    private String securityKey;

    /**
     * 阿里云短信特有-区域id
     */
    private String regionId;

    /**
     * 短信服务商短信签名
     */
    private String sign;

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"smsType\":")
                .append(smsType);
        sb.append(",\"appid\":\"")
                .append(appid).append('\"');
        sb.append(",\"accessKey\":\"")
                .append(accessKey).append('\"');
        sb.append(",\"securityKey\":\"")
                .append(securityKey).append('\"');
        sb.append(",\"regionId\":\"")
                .append(regionId).append('\"');
        sb.append(",\"sign\":\"")
                .append(sign).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
