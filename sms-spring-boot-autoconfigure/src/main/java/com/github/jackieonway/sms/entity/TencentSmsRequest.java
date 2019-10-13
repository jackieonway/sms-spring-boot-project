package com.github.jackieonway.sms.entity;

import java.util.Arrays;

/**
 * @author Jackie
 * @version \$id: TencentSmsRequest.java v 0.1 2019-05-11 16:15 Jackie Exp $$
 */
public class TencentSmsRequest extends BaseRequest {

    private String nationCode = "86";

    private String[] phoneNumber;

    private String[] params;

    private String extend = "";

    private String ext = "";

    private String msg;

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String[] getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String[] phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"nationCode\":\"")
                .append(nationCode).append('\"');
        sb.append(",\"phoneNumber\":")
                .append(Arrays.toString(phoneNumber));
        sb.append(",\"params\":")
                .append(Arrays.toString(params));
        sb.append(",\"extend\":\"")
                .append(extend).append('\"');
        sb.append(",\"ext\":\"")
                .append(ext).append('\"');
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
