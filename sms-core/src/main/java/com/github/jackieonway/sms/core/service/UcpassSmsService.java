package com.github.jackieonway.sms.core.service;

import com.github.jackieonway.sms.core.entity.BaseRequest;
import com.github.jackieonway.sms.core.entity.SmsProperties;
import com.github.jackieonway.sms.core.entity.UcpassSmsRequest;
import com.github.jackieonway.sms.core.exception.SmsException;
import com.github.jackieonway.sms.core.utls.SmsCacheUtils;
import com.github.jackieonway.sms.ucpass.client.JsonReqClient;
import com.github.jackieonway.sms.ucpass.entity.UcpassSmsProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 云之讯短信服务
 *
 * @author bigbearLoveTingting
 * @version \$id: UcpassSmsService.java v 0.1 2019-7-15 14:01 bigbearLoveTingting Exp $$
 */
public class UcpassSmsService implements SmsService {
    private static final String PARAM_CAN_NOT_BE_NULL = "param can not be null";
    private final JsonReqClient jsonReqClient;

    private final SmsProperties smsProperties;

    public UcpassSmsService(SmsProperties smsProperties) {
        UcpassSmsProperties ucpassSmsProperties
                = new UcpassSmsProperties();
        ucpassSmsProperties.setRestServer(smsProperties.getDomain());
        this.smsProperties = smsProperties;
        this.jsonReqClient = new JsonReqClient(ucpassSmsProperties);
    }

    /**
     * 批量发送
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     * @throws SmsException 异常
     * @see #sendTemplateSms(String, BaseRequest)
     */
    @Override
    public Object sendSms(@NonNull BaseRequest params) {
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
        return sendTemplateSms(params.getTemplateId(), params);
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
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
            SmsCacheUtils.cacheSms(((UcpassSmsRequest) params).getMobile(), params, smsProperties);
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
    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) {
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
        return sendBatchTemplateSms(params.getTemplateId(), params);
    }

    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
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
            SmsCacheUtils.cacheSms(((UcpassSmsRequest) params).getMobile(), params, smsProperties);
            return jsonReqClient.sendSmsBatch(sid, token, appid, templateId, param, mobile, uid);
        }
        throw new SmsException();
    }
}
