package com.github.jackieonway.sms.core.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.jackieonway.sms.commons.exception.SmsException;
import com.github.jackieonway.sms.commons.utils.GsonUtils;
import com.github.jackieonway.sms.core.entity.AliSmsRequest;
import com.github.jackieonway.sms.core.entity.BaseRequest;
import com.github.jackieonway.sms.core.entity.SmsProperties;
import com.github.jackieonway.sms.core.utls.SmsCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * 阿里云短信服务
 *
 * @author Jackie
 * @version \$id: AliSmsService.java v 0.1 2019-05-17 21:46 Jackie Exp $$
 * @see <a href="https://help.aliyun.com/document_detail/102715.html?spm=5176.8195934.1283918.7.48aa6a7d88zLMi#concept-t4w-pcs-ggb"></a>
 */
public class AliSmsService implements SmsService {
    private static final String PHONE_NUMBERS = "PhoneNumbers";
    private static final String PARAM_CAN_NOT_BE_NULL = "param can not be null";
    private static final String SEND_SMS = "SendSms";
    private final IAcsClient iAcsClient;

    private final SmsProperties smsProperties;

    public AliSmsService(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
        DefaultProfile defaultProfile = DefaultProfile.getProfile(smsProperties.getRegion(),
                smsProperties.getAccessKey(), smsProperties.getSecurityKey());
        this.iAcsClient = new DefaultAcsClient(defaultProfile);
    }

    @Override
    public Object sendSms(@NonNull BaseRequest params) {
        Assert.notNull(params, "param  can not be null");
        CommonRequest request = setCommonRequest();
        request.setSysAction(SEND_SMS);
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request, aliSmsRequest, aliSmsRequest.getTemplateId());
            setOtherParams(request, aliSmsRequest);
            try {
                SmsCacheUtils.cacheSms(Arrays.toString(aliSmsRequest.getPhoneNumbers()), params, smsProperties);
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
        CommonRequest request = setCommonRequest();
        request.setSysAction(SEND_SMS);
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request, aliSmsRequest, templateId);
            setOtherParams(request, aliSmsRequest);
            try {
                SmsCacheUtils.cacheSms(Arrays.toString(aliSmsRequest.getPhoneNumbers()), params, smsProperties);
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) {
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
        CommonRequest request = setCommonRequest();
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            String templateCode = aliSmsRequest.getTemplateId();
            setMultiSmsParams(request, aliSmsRequest, templateCode);
            setOtherParams(request, aliSmsRequest);
            try {
                SmsCacheUtils.cacheSms(Arrays.toString(aliSmsRequest.getPhoneNumbers()), params, smsProperties);
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }


    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, PARAM_CAN_NOT_BE_NULL);
        CommonRequest request = setCommonRequest();
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setMultiSmsParams(request, aliSmsRequest, templateId);
            setOtherParams(request, aliSmsRequest);
            try {
                SmsCacheUtils.cacheSms(Arrays.toString(aliSmsRequest.getPhoneNumbers()), params, smsProperties);
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    private CommonRequest setCommonRequest() {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(this.smsProperties.getDomain());
        request.setSysVersion(this.smsProperties.getVersion());
        return request;
    }

    private void setMultiSmsParams(CommonRequest request, AliSmsRequest aliSmsRequest, String templateCode) {
        if (StringUtils.isBlank(templateCode)) {
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode", templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null) {
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        if (aliSmsRequest.getIsSendBatchSms()) {
            request.setSysAction("SendBatchSms");
            request.putQueryParameter(PHONE_NUMBERS, GsonUtils.gson2String(phoneNumbers));
        } else {
            request.setSysAction(SEND_SMS);
            StringBuilder stringBuilder = new StringBuilder();
            for (String phoneNumber : phoneNumbers) {
                stringBuilder.append(phoneNumber).append(",");
            }
            request.putQueryParameter(PHONE_NUMBERS, stringBuilder.substring(0, stringBuilder.length() - 1));
        }
    }

    private void setSingleSmsParams(CommonRequest request, AliSmsRequest aliSmsRequest, String templateCode) {
        if (StringUtils.isBlank(templateCode)) {
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode", templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null) {
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        request.putQueryParameter(PHONE_NUMBERS, phoneNumbers[0]);
    }

    private void setOtherParams(CommonRequest request, AliSmsRequest aliSmsRequest) {
        if (StringUtils.isBlank(aliSmsRequest.getSignName())) {
            throw new IllegalArgumentException("param signName can not be null");
        }
        request.putQueryParameter("SignName", aliSmsRequest.getSignName());
        if (StringUtils.isNotBlank(smsProperties.getRegion())) {
            request.putQueryParameter("RegionId", smsProperties.getRegion());
        }
        if (StringUtils.isNotBlank(aliSmsRequest.getTemplateParam())) {
            request.putQueryParameter("TemplateParam", aliSmsRequest.getTemplateParam());
        }
        if (StringUtils.isNotBlank(aliSmsRequest.getSmsUpExtendCode())) {
            request.putQueryParameter("SmsUpExtendCode", aliSmsRequest.getSmsUpExtendCode());
        }
        if (StringUtils.isNotBlank(aliSmsRequest.getOutId())) {
            request.putQueryParameter("OutId", aliSmsRequest.getOutId());
        }
    }
}
