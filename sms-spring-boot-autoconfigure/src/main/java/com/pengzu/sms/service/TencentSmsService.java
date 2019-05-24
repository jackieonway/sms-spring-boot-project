package com.pengzu.sms.service;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;
import com.pengzu.sms.entity.SmsProperties;
import com.pengzu.sms.entity.TencentSmsRequest;

import java.io.IOException;

/**
 * @author Jackie
 * @version \$id: TencentSmsService.java v 0.1 2019-05-17 21:47 Jackie Exp $$
 */
public class TencentSmsService implements PengzuSmsService {

    private static final String SYS_TYPE_CONFIG_ERROR_MSG = "短信服务商信息配置错误";

    private SmsSingleSender smsSingleSender;

    private SmsMultiSender smsMultiSender;

    private SmsProperties smsProperties;

    public TencentSmsService(SmsSingleSender smsSingleSender,
                             SmsMultiSender smsMultiSender,SmsProperties smsProperties) {
        this.smsSingleSender = smsSingleSender;
        this.smsMultiSender = smsMultiSender;
        this.smsProperties = smsProperties;
    }

    @Override
    public Object sendSms(Integer type, Object params) throws HTTPException, IOException {
        if (params instanceof TencentSmsRequest){
            TencentSmsRequest tencentSmsRequest = (TencentSmsRequest) params;
            String nationCode = tencentSmsRequest.getNationCode();
            String phoneNumber = tencentSmsRequest.getPhoneNumber()[0];
            String extend = tencentSmsRequest.getExtend();
            String ext = tencentSmsRequest.getExt();
            String msg = tencentSmsRequest.getMsg();
            return smsSingleSender.send(type,nationCode, phoneNumber,
                    msg, extend, ext);
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    @Override
    public Object sendTemplateSms(String tempalteId, Object params) throws HTTPException, IOException {
        if (params instanceof TencentSmsRequest){
            TencentSmsRequest tencentSmsRequest = (TencentSmsRequest) params;
            String nationCode = tencentSmsRequest.getNationCode();
            String phoneNumber = tencentSmsRequest.getPhoneNumber()[0];
            String[] params1 = tencentSmsRequest.getParams();
            String sign = smsProperties.getSign();
            String extend = tencentSmsRequest.getExtend();
            String ext = tencentSmsRequest.getExt();
            return smsSingleSender.sendWithParam(nationCode, phoneNumber, Integer.parseInt(tempalteId),
                    params1, sign, extend, ext);
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    @Override
    public Object sendBatchSms(int type, Object params) throws HTTPException, IOException {
        if (params instanceof TencentSmsRequest){
            TencentSmsRequest tencentSmsRequest = (TencentSmsRequest) params;
            String nationCode = tencentSmsRequest.getNationCode();
            String[] phoneNumber = tencentSmsRequest.getPhoneNumber();
            String msg = tencentSmsRequest.getMsg();
            String extend = tencentSmsRequest.getExtend();
            String ext = tencentSmsRequest.getExt();
            return smsMultiSender.send(type,nationCode, phoneNumber, msg, extend, ext);
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    @Override
    public Object sendBatchTemplateSms(String tempalteId, Object params) throws HTTPException, IOException {
        if (params instanceof TencentSmsRequest){
            TencentSmsRequest tencentSmsRequest = (TencentSmsRequest) params;
            String nationCode = tencentSmsRequest.getNationCode();
            String[] phoneNumber = tencentSmsRequest.getPhoneNumber();
            int templateId = Integer.parseInt(tempalteId);
            String[] params1 = tencentSmsRequest.getParams();
            String sign = smsProperties.getSign();
            String extend = tencentSmsRequest.getExtend();
            String ext = tencentSmsRequest.getExt();
            return smsMultiSender.sendWithParam(nationCode, phoneNumber, templateId,
                    params1, sign, extend, ext);
        }
        throw new IllegalStateException(SYS_TYPE_CONFIG_ERROR_MSG);
    }
}
