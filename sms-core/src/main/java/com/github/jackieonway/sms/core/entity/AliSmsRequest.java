package com.github.jackieonway.sms.core.entity;

import java.util.Arrays;

/**
 * 阿里云sms请求参数
 *
 * @author Jackie
 * @version \$id: AliSmsRequest.java v 0.1 2019-05-17 21:19 Jackie Exp $$
 * @see <a href="https://help.aliyun.com/document_detail/101414.html?spm=a2c4g.11186623.2.13.18d73e2c7CYL6S"></a>
 */
public class AliSmsRequest extends BaseRequest {
    private static final long serialVersionUID = 7970787199107653507L;
    /**
     * 手机号
     */
    private String[] phoneNumbers;

    /**
     * 签名
     */
    private String signName;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 模板id参数
     */
    private String templateParam;

    /**
     * 上行短信扩展码
     */
    private String smsUpExtendCode;

    /**
     * 外部流水扩展字段
     */
    private String outId;

    private boolean isSendBatchSms = false;

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    @Override
    public String getTemplateId() {
        return templateId;
    }

    @Override
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public boolean getIsSendBatchSms() {
        return isSendBatchSms;
    }

    public void setIsSendBatchSms(boolean isSendBatchSms) {
        this.isSendBatchSms = isSendBatchSms;
    }

    @Override
    public String toString() {
        return "AliSmsRequest{" +
                "phoneNumbers=" + Arrays.toString(phoneNumbers) +
                ", signName='" + signName + '\'' +
                ", templateId='" + templateId + '\'' +
                ", templateParam='" + templateParam + '\'' +
                ", smsUpExtendCode='" + smsUpExtendCode + '\'' +
                ", outId='" + outId + '\'' +
                ", isSendBatchSms=" + isSendBatchSms +
                '}';
    }
}
