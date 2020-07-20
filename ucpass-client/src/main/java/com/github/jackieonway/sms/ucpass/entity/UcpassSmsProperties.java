package com.github.jackieonway.sms.ucpass.entity;

/**
 * @author Jackie
 * @version \$id: UcpassSmsProperties.java v 0.1 2019-05-11 10:03 Jackie Exp $$
 */
public class UcpassSmsProperties {

    /**
     *  云之讯服务地址
     */
    private String restServer;


    public String getRestServer() {
        return restServer;
    }

    public void setRestServer(String restSserver) {
        this.restServer = restSserver;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(",\"restServer\":\"").append(restServer).append('\"');
        sb.append("}");
        return sb.toString();
    }
}
