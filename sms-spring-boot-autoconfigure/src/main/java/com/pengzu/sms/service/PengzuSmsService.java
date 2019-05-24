package com.pengzu.sms.service;

import com.aliyuncs.exceptions.ClientException;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Jackie
 * @version \$id: SimplePengzuSmsServiceImpl.java v 0.1 2019-05-11 10:11 Jackie Exp $$
 */
@Component
public interface PengzuSmsService {

    /**
     *  单个发送短信
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendSms(Integer type,Object params) throws HTTPException, IOException, ClientException;

    /**
     * 单个发送模板短信
     * @param tempalteId 短信模板id
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendTemplateSms(String tempalteId, Object params) throws HTTPException, IOException, ClientException;

    /**
     *  批量发送短信
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendBatchSms(int type,Object params) throws HTTPException, IOException, ClientException;

    /**
     * 批量发送模板短信
     * @param tempalteId 短信模板id
     * @param params 根据对应的短信服务商所需信息填写
     */
    public Object sendBatchTemplateSms(String tempalteId, Object params) throws HTTPException, IOException, ClientException;
}
