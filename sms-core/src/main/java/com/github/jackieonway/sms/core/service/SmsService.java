package com.github.jackieonway.sms.core.service;

import com.github.jackieonway.sms.core.entity.BaseRequest;
import com.github.jackieonway.sms.core.utls.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * @author Jackie
 * @version \$id: SmsService.java v 0.1 2019-05-11 10:11 Jackie Exp $$
 */
public interface SmsService {

    Logger log = LoggerFactory.getLogger(SmsService.class);

    /**
     * 单个发送短信
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     */
    Object sendSms(BaseRequest params);

    /**
     * 单个发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     * @return Object
     */
    Object sendTemplateSms(@NonNull String templateId, BaseRequest params);

    /**
     * 批量发送短信
     *
     * @param params 根据对应的短信服务商所需信息填写
     * @return Object
     */
    Object sendBatchSms(@NonNull BaseRequest params);

    /**
     * 批量发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     * @return Object
     */
    Object sendBatchTemplateSms(@NonNull String templateId, BaseRequest params);

    /**
     * 异步单个发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     */
    default void asyncSendTemplateSms(@NonNull String templateId, BaseRequest params) {
        ThreadPoolUtils.smsAsyncTaskExecutor().execute(() -> {
            Object sendTemplateSms = this.sendTemplateSms(templateId, params);
            log.info("Async Thread [{}] send sms , request templateId:[{}], " +
                            "request prams:[{}], result:[{}]", Thread.currentThread().getName(), templateId, params,
                    sendTemplateSms);
        });

    }

    /**
     * 异步批量发送模板短信
     *
     * @param templateId 短信模板id
     * @param params     根据对应的短信服务商所需信息填写
     */
    default void asnycSendBatchTemplateSms(@NonNull String templateId, BaseRequest params) {
        ThreadPoolUtils.smsAsyncTaskExecutor().execute(() -> {
            Object sendTemplateSms = this.sendTemplateSms(templateId, params);
            log.info("Async Thread [{}] send batch sms , request templateId:[{}], " +
                            "request prams:[{}], result:[{}]", Thread.currentThread().getName(), templateId, params,
                    sendTemplateSms);
        });

    }
}
