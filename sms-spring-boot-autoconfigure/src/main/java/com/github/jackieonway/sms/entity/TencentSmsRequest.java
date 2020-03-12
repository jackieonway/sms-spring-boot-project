package com.github.jackieonway.sms.entity;

import java.util.Arrays;

/***
 * 腾讯短信内容 https://github.com/qcloudsms/qcloudsms_java/blob/master/src/main/java/com/github/qcloudsms/SmsSingleSender.java
 * @author Jackie
 * @version \$id: TencentSmsRequest.java v 0.1 2019-05-11 16:15 Jackie Exp $$
 */
public class TencentSmsRequest extends BaseRequest {

    /**
     * 国家码，如 86 为中国
     */
    private String nationCode = "86";

    /**
     * 不带国家码的手机号
     */
    private String[] phoneNumber;

    /**
     * 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
     */
    private String[] params;

    /**
     * 扩展码，可填空
     */
    private String extend = "";

    /**
     * 服务端原样返回的参数，可填空
     */
    private String ext = "";

    /**
     * 信息内容，必须与申请的模板格式一致，否则将返回错误
     */
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
