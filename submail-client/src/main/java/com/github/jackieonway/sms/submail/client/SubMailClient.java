package com.github.jackieonway.sms.submail.client;

import com.github.jackieonway.sms.core.exception.SmsException;
import com.github.jackieonway.sms.submail.model.MultiParams;
import com.github.jackieonway.sms.submail.model.SubMailParams;
import com.github.jackieonway.sms.submail.model.SubMailProperties;
import com.github.jackieonway.sms.core.utils.GsonUtils;
import com.github.jackieonway.sms.core.utils.OkHttpClientUtil;
import com.github.jackieonway.sms.submail.utils.RequestEncoder;
import com.github.jackieonway.sms.submail.utils.SignTypeEnum;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * submail send client
 *
 * @author bing_huang
 * @since V1.0 2020/07/09 7:49
 */
public class SubMailClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubMailClient.class);
    private static final String MESSAGE_SEND_URL = "https://api.mysubmail.com/message/send.json";
    private static final String MESSAGE_BATCH_SEND_URL = "https://api.mysubmail.com/message/multisend.json";
    private static final String MESSAGE_TEMPLATE_SEND_URL = "https://api.mysubmail.com/message/xsend.json";
    private static final String MESSAGE_BATCH_TEMPLATE_SEND_URL = "https://api.mysubmail.com/message/multixsend.json";
    private static final String API_TIMESTAMP = "http://api.submail.cn/service/timestamp.json";
    private static final String SIGNATURE = "signature";
    private static final String APPID = "appid";
    private static final String SIGN_TYPE = "sign_type";
    private static final String TIMESTAMP = "timestamp";
    private static final String TAG = "tag";
    private final SubMailProperties properties;

    public SubMailClient(SubMailProperties properties) {
        this.properties = properties;
        Assert.hasText(this.properties.getAppid(), "appid must not null");
        Assert.hasText(this.properties.getAppKey(), "appKey must not null");
    }

    /**
     * Single send message
     *
     * @param to      recipient phoneNumber(接收者手机号码)
     * @param content Text message(短信正文)
     * @param params  subMail request other request params(其余参数)
     * @return result
     */
    public String sendSms(@NonNull String to, @NonNull String content, SubMailParams params) {
        if (params == null) {
            params = new SubMailParams();
        }
        Assert.hasText(to, "`to` must not null");
        Assert.hasText(content, "`content` must not null");
        Map<String, Object> maps = new HashMap<>();
        maps.put("to", to);
        maps.put("content", content);
        return send(MESSAGE_SEND_URL, params, maps);
    }

    /**
     * send template message
     *
     * @param to        接收者手机号
     * @param projectId 项目标记id
     * @param vars      文本变量 json格式
     * @param params    其余参数
     * @return message result
     */
    public String sendTemplateSms(@NonNull String to, @NonNull String projectId, String vars, SubMailParams params) {
        if (params == null) {
            params = new SubMailParams();
        }
        Assert.hasText(to, "`to` must not blank");
        Assert.hasText(projectId, "`project` must not blank");
        Map<String, Object> maps = new HashMap<>();
        maps.put("to", to);
        maps.put("project", projectId);
        if (StringUtils.hasText(vars)) {
            maps.put("vars", vars);
        }
        return send(MESSAGE_TEMPLATE_SEND_URL, params, maps);
    }

    /**
     * 批量
     *
     * @param content 正文 must not null
     * @param multi   批量模式 must not null
     * @param params  请求参数
     * @return result message
     */
    public String sendBatchSms(@NonNull String content, @NonNull List<MultiParams> multi, SubMailParams params) {
        if (params == null) {
            params = new SubMailParams();
        }
        Assert.hasText(content, "`content` must not blank");
        Assert.notEmpty(multi, "multi must not empty");
        Map<String, Object> maps = new HashMap<>();
        maps.put("content", content);
        maps.put("multi", GsonUtils.gson2String(multi));
        return send(MESSAGE_BATCH_SEND_URL, params, maps);
    }

    /**
     * 批量发送模板消息
     *
     * @param projectId 项目标记 (ID)
     * @param multi     整合模式
     * @param params    请求参数
     * @return result message
     */
    public String sendBatchTemplateSms(@NonNull String projectId, List<MultiParams> multi, SubMailParams params) {
        if (params == null) {
            params = new SubMailParams();
        }
        Assert.hasText(projectId, "`projectId` must not blank");
        Assert.notEmpty(multi, "`multi` must not empty");
        Map<String, Object> maps = new HashMap<>();
        maps.put("project", projectId);
        maps.put("multi", GsonUtils.gson2String(multi));
        return send(MESSAGE_BATCH_TEMPLATE_SEND_URL, params, maps);
    }

    private String send(String url, SubMailParams params, Map<String, Object> map) {
        Map<String, Object> build = build(map, params);
        try {
            Response response = OkHttpClientUtil.getInstance().postJson(url, GsonUtils.gson2String(build));
            String result = Objects.requireNonNull(response.body()).string();
            LOGGER.info("send sub mail result {}", result);
            return result;
        } catch (Exception e) {
            throw new SmsException("send subMail Error", e);
        }
    }


    protected Map<String, Object> build(Map<String, Object> data, SubMailParams params) {
        data.put(APPID, this.properties.getAppid());
        data.put(TIMESTAMP, this.getTimestamp());
        data.put(SIGN_TYPE, params.getSignType());
        String signature = createSignature(RequestEncoder.formatRequest(data), params.getSignType());
        data.put(SIGNATURE, signature);
        if (StringUtils.hasText(params.getTag())) {
            data.put(TAG, params.getTag());
        }
        return data;
    }

    protected String createSignature(String data, @NonNull String sinType) {
        if (SignTypeEnum.NORMAL.getValue().equals(sinType)) {
            return this.properties.getAppKey();
        } else {
            return buildSignature(data, sinType);
        }
    }

    private String buildSignature(String data, @NonNull String signType) {
        Assert.hasText(signType, "sign type can not be blank");
        String app = this.properties.getAppid();
        String appKey = this.properties.getAppKey();
        // order is confirmed
        String jointData = app + appKey + data + app + appKey;
        if (SignTypeEnum.MD5.getValue().equals(signType)) {
            return RequestEncoder.encode(RequestEncoder.MD5, jointData);
        } else if (SignTypeEnum.SHA1.getValue().equals(signType)) {
            return RequestEncoder.encode(RequestEncoder.SHA1, jointData);
        }
        return null;
    }

    protected String getTimestamp() {
        String json = null;
        try {
            Response data = OkHttpClientUtil.getInstance().getData(API_TIMESTAMP);
            json = Objects.requireNonNull(data.body()).string();
            SubMailClient.Timestamp timestamp = GsonUtils.gsonToBean(json, SubMailClient.Timestamp.class);
            return timestamp.getTimestamp();
        } catch (Exception e) {
            LOGGER.error("获取时间戳失败", e);
        }
        return json;
    }

    static class Timestamp {
        private String timestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
