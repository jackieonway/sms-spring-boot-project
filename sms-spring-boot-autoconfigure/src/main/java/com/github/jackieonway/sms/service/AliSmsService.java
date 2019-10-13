package com.github.jackieonway.sms.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.github.jackieonway.sms.entity.AliSmsRequest;
import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.exception.SmsException;
import org.springframework.util.StringUtils;

/**
 * @author Jackie
 * @version \$id: AliSmsService.java v 0.1 2019-05-17 21:46 Jackie Exp $$
 */
public class AliSmsService implements SmsService {

    private static final String DYSMSAPI_ALIYUNCS_COM = "dysmsapi.aliyuncs.com";
    private static final String SYS_VERSION = "2017-05-25";
    private static final String SYS_TYPE_CONFIG_ERROR_MSG = "短信服务商信息配置错误";

    private IAcsClient iAcsClient;

    private SmsProperties smsProperties;

    public AliSmsService(IAcsClient iAcsClient, SmsProperties smsProperties) {
        this.iAcsClient = iAcsClient;
        this.smsProperties = smsProperties;
    }

    @Override
    public Object sendSms(Integer type, BaseRequest params) throws SmsException {
        CommonRequest request = setCommonRequest();
        request.setSysAction("SendSms");
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request, aliSmsRequest,aliSmsRequest.getTemplateCode());
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    @Override
    public Object sendTemplateSms(String tempalteId, BaseRequest params) throws SmsException{
        CommonRequest request = setCommonRequest();
        request.setSysAction("SendSms");
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setSingleSmsParams(request,aliSmsRequest,tempalteId);
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    @Override
    public Object sendBatchSms(int type, BaseRequest params) throws SmsException {
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
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }


    @Override
    public Object sendBatchTemplateSms(String tempalteId, BaseRequest params) throws SmsException {
        CommonRequest request = setCommonRequest();
        if (params instanceof AliSmsRequest) {
            AliSmsRequest aliSmsRequest = (AliSmsRequest) params;
            setMultiSmsParams(request,aliSmsRequest,tempalteId);
            setOtherParams(request, aliSmsRequest);
            try {
                return iAcsClient.getCommonResponse(request);
            } catch (ClientException e) {
                throw new SmsException(e);
            }
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    private CommonRequest setCommonRequest() {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DYSMSAPI_ALIYUNCS_COM);
        request.setSysVersion(SYS_VERSION);
        return request;
    }

    private void setMultiSmsParams(CommonRequest request, AliSmsRequest aliSmsRequest, String templateCode) {
        if (!StringUtils.hasText(templateCode)){
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode",templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null){
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        if (aliSmsRequest.getIsSendBatchSms()) {
            request.setSysAction("SendBatchSms");
			request.putQueryParameter("PhoneNumbers", JSON.toJSONString(phoneNumbers));
		}else {
            request.setSysAction("SendSms");
			StringBuilder stringBuilder = new StringBuilder();
			for (String phoneNumber : phoneNumbers) {
				stringBuilder.append(phoneNumber).append(",");
			}
			request.putQueryParameter("PhoneNumbers", stringBuilder.substring(0, stringBuilder.length() - 1));
		}
    }

    private void setSingleSmsParams(CommonRequest request, AliSmsRequest aliSmsRequest,String templateCode) {
        if (!StringUtils.hasText(templateCode)){
            throw new IllegalArgumentException("param templateCode can not be null");
        }
        request.putQueryParameter("TemplateCode",templateCode);
        String[] phoneNumbers = aliSmsRequest.getPhoneNumbers();
        if (phoneNumbers == null){
            throw new IllegalArgumentException("param phoneNumbers can not be null");
        }
        request.putQueryParameter("PhoneNumbers",phoneNumbers[0]);
    }

    private void setOtherParams(CommonRequest request, AliSmsRequest aliSmsRequest) {
        if (!StringUtils.hasText(aliSmsRequest.getSignName())) {
            throw new IllegalArgumentException("param signName can not be null");
        }
        request.putQueryParameter("SignName", aliSmsRequest.getSignName());
        if (StringUtils.hasText(smsProperties.getRegionId())) {
            request.putQueryParameter("RegionId", smsProperties.getRegionId());
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
