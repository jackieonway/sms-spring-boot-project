package com.github.jackieonway.sms.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.jackieonway.sms.entity.AliSmsRequest;
import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.exception.SmsException;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 阿里云短信服务
 *
 * @author Jackie
 * @version \$id: AliSmsService.java v 0.1 2019-05-17 21:46 Jackie Exp $$
 * @see <a href="https://help.aliyun.com/document_detail/102715.html?spm=5176.8195934.1283918.7.48aa6a7d88zLMi#concept-t4w-pcs-ggb"></a>
 */
public class AliSmsService implements SmsService {
    private final IAcsClient iAcsClient;

    private final SmsProperties smsProperties;

    public AliSmsService(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
        DefaultProfile defaultProfile = DefaultProfile.getProfile(smsProperties.getRegion(),
                smsProperties.getAccessKey(), smsProperties.getSecurityKey());
        this.iAcsClient = new DefaultAcsClient(defaultProfile);
    }

    @Override
    public Object sendSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param  can not be null");
        CommonRequest request = setCommonRequest();
        request.setSysAction("SendSms");
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request, aliSmsRequest, aliSmsRequest.getTemplateCode());
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        CommonRequest request = setCommonRequest();
        request.setSysAction("SendSms");
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request, aliSmsRequest, templateId);
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }

    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "param can not be null");
        CommonRequest request = setCommonRequest();
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            String templateCode = aliSmsRequest.getTemplateCode();
            setMultiSmsParams(request, aliSmsRequest, templateCode);
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new SmsException();
    }


    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, @NonNull BaseRequest params) throws SmsException {
        Assert.notNull(templateId, "templateId can not be null");
        Assert.notNull(params, "param can not be null");
        CommonRequest request = setCommonRequest();
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setMultiSmsParams(request, aliSmsRequest, templateId);
            setOtherParams(request, aliSmsRequest);
            try {
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
        if (!StringUtils.hasText(templateCode)) {
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode", templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null) {
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        if (aliSmsRequest.getIsSendBatchSms()) {
            request.setSysAction("SendBatchSms");
            request.putQueryParameter("PhoneNumbers", JSON.toJSONString(phoneNumbers));
        } else {
            request.setSysAction("SendSms");
            StringBuilder stringBuilder = new StringBuilder();
            for (String phoneNumber : phoneNumbers) {
                stringBuilder.append(phoneNumber).append(",");
            }
            request.putQueryParameter("PhoneNumbers", stringBuilder.substring(0, stringBuilder.length() - 1));
        }
    }

    private void setSingleSmsParams(CommonRequest request, AliSmsRequest aliSmsRequest, String templateCode) {
        if (!StringUtils.hasText(templateCode)) {
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode", templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null) {
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        request.putQueryParameter("PhoneNumbers", phoneNumbers[0]);
    }

    private void setOtherParams(CommonRequest request, AliSmsRequest aliSmsRequest) {
        if (!StringUtils.hasText(aliSmsRequest.getSignName())) {
            throw new IllegalArgumentException("param signName can not be null");
        }
        request.putQueryParameter("SignName", aliSmsRequest.getSignName());
        if (StringUtils.hasText(smsProperties.getRegion())) {
            request.putQueryParameter("RegionId", smsProperties.getRegion());
        }
        if (StringUtils.hasText(aliSmsRequest.getTemplateParam())) {
            request.putQueryParameter("TemplateParam", aliSmsRequest.getTemplateParam());
        }
        if (StringUtils.hasText(aliSmsRequest.getSmsUpExtendCode())) {
            request.putQueryParameter("SmsUpExtendCode", aliSmsRequest.getSmsUpExtendCode());
        }
        if (StringUtils.hasText(aliSmsRequest.getOutId())) {
            request.putQueryParameter("OutId", aliSmsRequest.getOutId());
        }
    }
}
