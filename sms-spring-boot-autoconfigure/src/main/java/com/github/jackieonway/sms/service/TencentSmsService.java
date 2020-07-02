package com.github.jackieonway.sms.service;

import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.TencentSmsRequest;
import com.github.jackieonway.sms.exception.SmsException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackie
 * @version \$id: TencentSmsService.java v 0.1 2019-05-17 21:47 Jackie Exp $$
 */
public class TencentSmsService implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TencentSmsService.class);
    private final SmsProperties properties;
    private final SmsClient smsClient;

    public TencentSmsService(SmsProperties properties) {
        this.properties = properties;
        String accessKey = this.properties.getAccessKey();
        String securityKey = this.properties.getSecurityKey();
        Credential credential = new Credential(accessKey, securityKey);
        ClientProfile profile = new ClientProfile();
        this.smsClient = new SmsClient(credential, this.properties.getRegion(), profile);
    }

    @Override
    public Object sendSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param can not be null");
        if (params instanceof TencentSmsRequest) {
            sendBatchSms(params);
        }
        throw new SmsException();
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        if (params instanceof TencentSmsRequest) {
            sendBatchTemplateSms(templateId, params);
        }
        throw new SmsException();
    }

    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param can not be null");
        if (params instanceof TencentSmsRequest) {
            sendBatchTemplateSms(params.getTemplateCode(), params);
        }
        throw new SmsException();
    }

    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        if (params instanceof TencentSmsRequest) {
            SendSmsRequest request = getRequest();
            TencentSmsRequest tencentParams = (TencentSmsRequest) params;
            setMultiSmsParams(request, tencentParams, templateId);
            setOtherParams(request, tencentParams);
            try {
                return smsClient.SendSms(request);
            } catch (TencentCloudSDKException e) {
                e.printStackTrace();
                LOGGER.error("send message error", e);
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    @NonNull
    private SendSmsRequest getRequest() {
        String appid = this.properties.getAppid();
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppid(appid);
        return request;
    }

    private void setMultiSmsParams(SendSmsRequest request, TencentSmsRequest params, @NonNull String templateId) {
        String[] phoneNumbers = params.getPhoneNumber();
        Assert.notEmpty(phoneNumbers, "request params phone number can not be null");
        Assert.hasText(templateId, "request params template can not null");

        List<String> lists = new ArrayList<>();
        if (params.isSendBatchSms()) {
            lists.add("+" + params.getNationCode() + phoneNumbers[0]);
        } else {
            for (String number : phoneNumbers) {
                lists.add("+" + params.getNationCode() + number);
            }
        }
        phoneNumbers = lists.toArray(new String[]{});
        request.setTemplateID(templateId);
        request.setPhoneNumberSet(phoneNumbers);
    }

    private void setOtherParams(SendSmsRequest request, TencentSmsRequest params) {
        String sign = params.getSign();
        Assert.hasText(sign, "request params sign can not be null");
        request.setSign(sign);
        if (StringUtils.hasText(params.getSenderId())) {
            request.setSenderId(params.getSenderId());
        }
        if (StringUtils.hasText(params.getExt())) {
            request.setSessionContext(params.getExt());
        }
        if (StringUtils.hasText(params.getExtend())) {
            request.setExtendCode(params.getExtend());
        }
        if (params.getParams().length > 0) {
            request.setTemplateParamSet(params.getParams());
        }
    }
}
