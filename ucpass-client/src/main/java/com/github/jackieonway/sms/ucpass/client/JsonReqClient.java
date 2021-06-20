package com.github.jackieonway.sms.ucpass.client;


import com.github.jackieonway.sms.commons.utils.GsonUtils;
import com.github.jackieonway.sms.commons.utils.OkHttpClientUtil;
import com.github.jackieonway.sms.ucpass.entity.UcpassSmsProperties;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JsonReqClient {

    private static final String SEND_SMS_ERROR = "send sms error";
    private static final String TOKEN = "token";
    private static final String APPID = "appid";
    private static final String TEMPLATEID = "templateid";
    private static final String BODY = "body : {}";
    private Logger logger = LoggerFactory.getLogger(JsonReqClient.class);

    private UcpassSmsProperties smsProperties;

    public JsonReqClient(UcpassSmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    private StringBuffer getStringBuffer() {
        StringBuffer sb = new StringBuffer("https://");
        sb.append(smsProperties.getRestServer()).append("/ol/sms");
        return sb;
    }

    public Response sendSms(String sid, String token, String appid, String templateid, String param, String mobile,
                            String uid) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/sendsms").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put(TEMPLATEID, templateid);
            jsonObject.put("param", param);
            jsonObject.put("mobile", mobile);
            jsonObject.put("uid", uid);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);
        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

    public Response sendSmsBatch(String sid, String token, String appid, String templateid, String param, String mobile,
                                 String uid) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/sendsms_batch").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put(TEMPLATEID, templateid);
            jsonObject.put("param", param);
            jsonObject.put("mobile", mobile);
            jsonObject.put("uid", uid);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);
        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

    public Response addSmsTemplate(String sid, String token, String appid, String type, String templateName,
                                   String autograph, String content) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/addsmstemplate").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put("type", type);
            jsonObject.put("template_name", templateName);
            jsonObject.put("autograph", autograph);
            jsonObject.put("content", content);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);
        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

    public Response getSmsTemplate(String sid, String token, String appid, String templateid, String pageNum,
                                   String pageSize) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/getsmstemplate").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put(TEMPLATEID, templateid);
            jsonObject.put("page_num", pageNum);
            jsonObject.put("page_size", pageSize);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);
        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

    public Response editSmsTemplate(String sid, String token, String appid, String templateid, String type,
                                    String templateName, String autograph, String content) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/editsmstemplate").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put(TEMPLATEID, templateid);
            jsonObject.put("type", type);
            jsonObject.put("template_name", templateName);
            jsonObject.put("autograph", autograph);
            jsonObject.put("content", content);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);
        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

    public Response deleterSmsTemplate(String sid, String token, String appid, String templateid) {
        Response result = null;
        try {
            String url = getStringBuffer().append("/deletesmstemplate").toString();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("sid", sid);
            jsonObject.put(TOKEN, token);
            jsonObject.put(APPID, appid);
            jsonObject.put(TEMPLATEID, templateid);
            String body = GsonUtils.gson2String(jsonObject);
            logger.info(BODY, body);
            result = OkHttpClientUtil.getInstance().postJson(url, body);

        } catch (Exception e) {
            logger.error(SEND_SMS_ERROR, e);
        }
        return result;
    }

}
