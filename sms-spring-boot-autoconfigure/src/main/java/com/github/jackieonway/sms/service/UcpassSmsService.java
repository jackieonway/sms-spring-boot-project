package com.github.jackieonway.sms.service;

import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.UcpassSmsRequest;
import com.github.jackieonway.sms.exception.SmsException;
import com.github.jackieonway.sms.ucpass.client.JsonReqClient;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 云之讯短信服务
 *
 * @author bigbearLoveTingting
 * @version \$id: UcpassSmsService.java v 0.1 2019-7-15 14:01 bigbearLoveTingting Exp $$
 */
public class UcpassSmsService implements SmsService {
    private final JsonReqClient jsonReqClient;

    private final SmsProperties smsProperties;

    public UcpassSmsService(SmsProperties smsProperties) {
        com.github.jackieonway.sms.ucpass.entity.SmsProperties smsProperties1
                = new com.github.jackieonway.sms.ucpass.entity.SmsProperties();
        smsProperties1.setRestSserver(smsProperties.getDomain());
        this.smsProperties = smsProperties;
        this.jsonReqClient = new JsonReqClient(smsProperties1);
    }

    /**
     * 批量发送
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     * @throws SmsException 异常
     * @see #sendTemplateSms(String, BaseRequest)
     */
    @Deprecated
    @Override
    public Object sendSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param can not be null");
        return sendTemplateSms(params.getTemplateId(), params);
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        if (params instanceof UcpassSmsRequest) {
            UcpassSmsRequest ucPassSmsRequest = (UcpassSmsRequest) params;
            // 发送的手机号
            String mobile = ucPassSmsRequest.getMobile().split(",")[0];
            // 参数
            String param = ucPassSmsRequest.getParam();
            String sid = smsProperties.getAccessKey();
            String token = smsProperties.getSecurityKey();
            String appid = smsProperties.getAppid();
            String uid = ucPassSmsRequest.getUid();
            return jsonReqClient.sendSms(sid, token, appid, templateId, param, mobile, uid);
        }
        throw new SmsException();
    }

    /**
     * 批量发送
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     * @throws SmsException 异常
     * @see #sendBatchTemplateSms(String, BaseRequest)
     */
    @Deprecated
    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param can not be null");
        return sendBatchTemplateSms(params.getTemplateId(), params);
    }

    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        if (params instanceof UcpassSmsRequest) {
            UcpassSmsRequest ucPassSmsRequest = (UcpassSmsRequest) params;
            // 发送的手机号
            String mobile = ucPassSmsRequest.getMobile();
            String uid = ucPassSmsRequest.getUid();
            // 参数
            String param = ucPassSmsRequest.getParam();
            String sid = smsProperties.getAccessKey();
            String token = smsProperties.getSecurityKey();
            String appid = smsProperties.getAppid();
            return jsonReqClient.sendSmsBatch(sid, token, appid, templateId, param, mobile, uid);
        }
        throw new SmsException();
    }
}
