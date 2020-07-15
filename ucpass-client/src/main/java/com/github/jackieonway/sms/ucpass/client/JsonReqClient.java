package com.github.jackieonway.sms.ucpass.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.jackieonway.sms.ucpass.entity.SmsProperties;
import com.github.jackieonway.sms.ucpass.HttpClientUtil;

public class JsonReqClient {

	private Logger logger = LoggerFactory.getLogger(JsonReqClient.class);

	private SmsProperties smsProperties;

	public JsonReqClient(SmsProperties smsProperties){
		this.smsProperties = smsProperties;
	}

	private StringBuffer getStringBuffer() {
		StringBuffer sb = new StringBuffer("https://");
		sb.append(smsProperties.getRestServer()).append("/ol/sms");
		return sb;
	}
	public String sendSms(String sid, String token, String appid, String templateid, String param, String mobile,
			String uid) {
		String result = "";
		try {
			String url = getStringBuffer().append("/sendsms").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateid);
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid", uid);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}

	public String sendSmsBatch(String sid, String token, String appid, String templateid, String param, String mobile,
			String uid) {
		String result = "";
		try {
			String url = getStringBuffer().append("/sendsms_batch").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateid);
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid", uid);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}

	public String addSmsTemplate(String sid, String token, String appid, String type, String template_name,
			String autograph, String content) {
		String result = "";
		try {
			String url = getStringBuffer().append("/addsmstemplate").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("type", type);
			jsonObject.put("template_name", template_name);
			jsonObject.put("autograph", autograph);
			jsonObject.put("content", content);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}

	public String getSmsTemplate(String sid, String token, String appid, String templateid, String page_num,
			String page_size) {
		String result = "";
		try {
			String url = getStringBuffer().append("/getsmstemplate").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateid);
			jsonObject.put("page_num", page_num);
			jsonObject.put("page_size", page_size);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}

	public String editSmsTemplate(String sid, String token, String appid, String templateid, String type,
			String template_name, String autograph, String content) {
		String result = "";
		try {
			String url = getStringBuffer().append("/editsmstemplate").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateid);
			jsonObject.put("type", type);
			jsonObject.put("template_name", template_name);
			jsonObject.put("autograph", autograph);
			jsonObject.put("content", content);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}

	public String deleterSmsTemplate(String sid, String token, String appid, String templateid) {
		String result = "";
		try {
			String url = getStringBuffer().append("/deletesmstemplate").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateid);
			String body = jsonObject.toJSONString();
			logger.info("body : {}" , body);
			result = HttpClientUtil.postJson(url, body, null);
			
		} catch (Exception e) {
			logger.error("send sms error", e);
		}
		return result;
	}
	
}
