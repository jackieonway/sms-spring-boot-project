package com.github.jackieonway.sms.ucpass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	public static String postJson(String url, String body, String charset) {
		
		String result = null;

		if (null == charset) {
			charset = "UTF-8";
		}
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = HttpConnectionManager.getInstance().getHttpClient();
			httpPost = new HttpPost(url);

			// 设置连接超时,设置读取超时
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(10000)
					.setSocketTimeout(10000)
					.build();
			httpPost.setConfig(requestConfig);

			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");

			// 设置参数
			StringEntity se = new StringEntity(body, "UTF-8");
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
