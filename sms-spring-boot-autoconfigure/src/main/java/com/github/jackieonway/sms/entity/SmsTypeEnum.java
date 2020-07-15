package com.github.jackieonway.sms.entity;

/**
 * @author Jackie
 * @version \$id: SmsType.java v 0.1 2019-05-11 10:29 Jackie Exp $$
 */
public enum SmsTypeEnum {

    /**
     * 阿里短信服务
     */
    ALI("ali", "阿里短信服务"),

    /**
     * 腾讯短信服务
     */
    TENCENT("tencent", "腾讯短信服务"),

    /**
     * 云之讯短信平台
     */
    UCPASS("ucpass", "云之讯短信服务"),
    /**
     * 赛邮云通信平台
     */
    SUBMAIL("submail", "赛邮短信服务"),
    ;

    private String type;

    private String desc;

    SmsTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public SmsTypeEnum getSmsTypeByType(String type) {
        if (type == null || type.trim().length() < 1) {
            return null;
        }
        for (SmsTypeEnum smsType : values()) {
            if (smsType.getType().equalsIgnoreCase(type)) {
                return smsType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"desc\":\"")
                .append(desc).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
