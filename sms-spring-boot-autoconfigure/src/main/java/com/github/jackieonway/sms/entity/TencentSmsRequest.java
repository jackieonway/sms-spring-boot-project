package com.github.jackieonway.sms.entity;

import java.util.Arrays;

/***
 * 腾讯短信内容
 *
 * @author Jackie
 * @version \$id: TencentSmsRequest.java v 0.1 2019-05-11 16:15 Jackie Exp $$
 * @see <a href="https://cloud.tencent.com/document/product/382/38778"></a>
 */
public class TencentSmsRequest extends BaseRequest {

    private static final long serialVersionUID = -5554838395436770847L;
    /**
     * 短信签名内容
     */
    private String sign;

    /**
     * 国际/港澳台短信 senderid
     */
    private String senderId;


    /**
     * 扩展码，可填空
     */
    private String extend;

    /**
     * 服务端原样返回的参数，可填空
     */
    private String ext;
    /**
     * 国家码，如 86 为中国
     */
    private String nationCode = "86";
    /**
     * 不带国家码的手机号
     */
    private String[] phoneNumber;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
     */
    private String[] params;

    private boolean isSendBatchSms = false;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    @Override
    public String getTemplateId() {
        return templateId;
    }

    @Override
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public boolean isSendBatchSms() {
        return isSendBatchSms;
    }

    public void setSendBatchSms(boolean sendBatchSms) {
        isSendBatchSms = sendBatchSms;
    }

    @Override
    public String toString() {
        return "TencentSmsRequest{" +
                "sign='" + sign + '\'' +
                ", senderId='" + senderId + '\'' +
                ", extend='" + extend + '\'' +
                ", ext='" + ext + '\'' +
                ", nationCode='" + nationCode + '\'' +
                ", phoneNumber=" + Arrays.toString(phoneNumber) +
                ", templateId='" + templateId + '\'' +
                ", params=" + Arrays.toString(params) +
                ", isSendBatchSms=" + isSendBatchSms +
                '}';
    }
}
