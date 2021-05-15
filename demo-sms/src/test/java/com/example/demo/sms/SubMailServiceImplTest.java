package com.example.demo.sms;

import com.github.jackieonway.sms.core.entity.SmsProperties;
import com.github.jackieonway.sms.core.entity.SubMailRequest;
import com.github.jackieonway.sms.core.service.SmsService;
import com.github.jackieonway.sms.core.service.SubMailSmsServiceImpl;
import com.github.jackieonway.sms.submail.utils.SignTypeEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * submail service test
 *
 * @author bing_huang
 * @date 2020/07/09 8:30
 * @since V1.0
 */
public class SubMailServiceImplTest {

    private static final String appid = "xxxx";
    private static final String appKey = "xxxxx";
    private static final String phoneNumber = "xxxx";
    private static final String EG = "【SUBMAIL】";
    // 无变量
    private static final String projectId = "PJrko2asdasd";
    // 有变量
    private static final String projectID2 = "ada";

    @Test
    public void sendSmsSHA() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent("尊敬的客户，您正在进行下单，短信验证码是:4438。感谢您的使用，本条短信不用回复。");
        params.setPhoneNumber(phoneNumber);
        params.setSignType(com.github.jackieonway.sms.submail.utils.SignTypeEnum.SHA1);
        Object o = service.sendSms(params);
        System.out.println(o);
    }

    @Test
    public void sendSms() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent(EG + "尊敬的客户，您正在进行下单，短信验证码是:4438。感谢您的使用，本条短信不用回复。");
        params.setPhoneNumber(phoneNumber);
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = service.sendSms(params);
        System.out.println(o);
    }

    @Test
    public void sendTemplateSms() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent("{\"code\":\"12312\"}");
        params.setPhoneNumber(phoneNumber);
        params.setSignType(SignTypeEnum.NORMAL);
        service.sendTemplateSms(projectID2, params);
    }

    @Test
    public void sendTemplateSmsMD5() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent("{\"code\":\"12312\"}");
        params.setPhoneNumber(phoneNumber);
        params.setSignType(SignTypeEnum.MD5);
        Object o = service.sendTemplateSms(projectID2, params);
        System.out.println(o);
    }

    @Test
    public void sendBatchSms() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent(EG + "尊敬的客户，您正在进行下单，短信验证码是: @var(code) 。感谢您的使用，本条短信不用回复。");
        params.setMulti(test());
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = service.sendBatchSms(params);
        System.out.println(o);
    }

    @Test
    public void sendBatchSmsMd5() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setContent(EG + "尊敬的客户，您正在进行下单，短信验证码是: @var(code) 。感谢您的使用，本条短信不用回复。");
        params.setMulti(test());
        params.setSignType(SignTypeEnum.MD5);
        Object o = service.sendBatchSms(params);
        System.out.println(o);
    }

    @Test
    public void sendBatchTemplateSms() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setMulti(test());
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = service.sendBatchTemplateSms(projectID2, params);
        System.out.println(o);
    }

    @Test
    public void sendBatchTemplateSmsMd5() {
        SmsProperties properties = new SmsProperties();
        properties.setAppid(appid);
        properties.setSecurityKey(appKey);
        SmsService service = new SubMailSmsServiceImpl(properties);
        SubMailRequest params = new SubMailRequest();
        params.setMulti(test());
        params.setSignType(SignTypeEnum.MD5);
        Object o = service.sendBatchTemplateSms(projectID2, params);
        System.out.println(o);
    }

    private List<SubMailRequest.Multi> test() {
        List<SubMailRequest.Multi> list = new ArrayList<>();
        SubMailRequest.Multi multi = new SubMailRequest.Multi();
        multi.setTo(phoneNumber);
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 123456);
        multi.setVars(maps);
        list.add(multi);
        return list;
    }
}
