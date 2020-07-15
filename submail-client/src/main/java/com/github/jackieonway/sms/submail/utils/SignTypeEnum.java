package com.github.jackieonway.sms.submail.utils;

/**
 * 签名类型
 *
 * @author bing_huang
 * @date 2020/07/09 7:40
 * @since V1.0
 */
public enum SignTypeEnum {
    /**
     * 无需签名
     */
    NORMAL("normal"),
    /**
     * md5
     */
    MD5("md5"),
    /**
     * sha1
     */
    SHA1("sha1");
    private String value;

    SignTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
