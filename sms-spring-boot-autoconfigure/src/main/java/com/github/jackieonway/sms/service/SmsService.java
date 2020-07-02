package com.github.jackieonway.sms.service;

import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.exception.SmsException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Jackie
 * @version \$id: SmsService.java v 0.1 2019-05-11 10:11 Jackie Exp $$
 */
@Component
public interface SmsService {

    /**
     * 单个发送短信
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     */
    public Object sendSms(BaseRequest params) throws SmsException;

    /**
     * 单个发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     * @return Object
     */
    public Object sendTemplateSms(@NonNull String templateId, BaseRequest params) throws SmsException;

    /**
     * 批量发送短信
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     */
    public Object sendBatchSms(@NonNull BaseRequest params) throws SmsException;

    /**
     * 批量发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     * @return Object
     */
    public Object sendBatchTemplateSms(@NonNull String templateId, BaseRequest params) throws SmsException;
}
