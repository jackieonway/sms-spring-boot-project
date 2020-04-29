package com.github.jackieonway.sms.ucpass.entity;

/**
 * @author Jackie
 * @version \$id: SmsProperties.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
public class SmsProperties {

    /**
     *  云之讯服务地址
     */
    private String restSserver;


    public String getRestSserver() {
        return restSserver;
    }

    public void setRestSserver(String restSserver) {
        this.restSserver = restSserver;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(",\"restSserver\":\"").append(restSserver).append('\"');
        sb.append("}");
        return sb.toString();
    }
}
