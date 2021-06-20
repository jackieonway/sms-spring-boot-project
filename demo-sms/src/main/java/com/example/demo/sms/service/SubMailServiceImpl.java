package com.example.demo.sms.service;

import com.github.jackieonway.sms.core.entity.SubMailRequest;
import com.github.jackieonway.sms.core.service.SmsService;
import com.github.jackieonway.sms.submail.utils.SignTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bing_huang
 * @date 2021/5/15
 * @since 1.0.0
 */
@Service
public class SubMailServiceImpl {
    private final SmsService subMailService;

    public SubMailServiceImpl(@Autowired(required = false) SmsService subMailService) {
        this.subMailService = subMailService;
    }

    public void sendSmsSHA(String phoneNo) {
        SubMailRequest params = new SubMailRequest();
        params.setContent("尊敬的客户，您正在进行下单，短信验证码是:4438。感谢您的使用，本条短信不用回复。");
        params.setPhoneNumber(phoneNo);
        params.setSignType(com.github.jackieonway.sms.submail.utils.SignTypeEnum.SHA1);
        Object o = subMailService.sendSms(params);
        System.out.println(o);
    }

    public void sendSms(String phoneNo, String eg) {
        SubMailRequest params = new SubMailRequest();
        params.setContent(eg + "尊敬的客户，您正在进行下单，短信验证码是:4438。感谢您的使用，本条短信不用回复。");
        params.setPhoneNumber(phoneNo);
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = subMailService.sendSms(params);
        System.out.println(o);
    }

    public void sendTemplateSms(String phoneNo, String templateId) {
        SubMailRequest params = new SubMailRequest();
        params.setContent("{\"code\":\"12312\"}");
        params.setPhoneNumber(phoneNo);
        params.setSignType(SignTypeEnum.NORMAL);
        subMailService.sendTemplateSms(templateId, params);
    }

    public void sendTemplateSmsMD5(String templateId, String phoneNo) {
        SubMailRequest params = new SubMailRequest();
        params.setContent("{\"code\":\"12312\"}");
        params.setPhoneNumber(phoneNo);
        params.setSignType(SignTypeEnum.MD5);
        Object o = subMailService.sendTemplateSms(templateId, params);
        System.out.println(o);
    }

    public void sendBatchSms(String phoneNo, String eg) {
        SubMailRequest params = new SubMailRequest();
        params.setContent(eg + "尊敬的客户，您正在进行下单，短信验证码是: @var(code) 。感谢您的使用，本条短信不用回复。");
        params.setMulti(test(phoneNo));
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = subMailService.sendBatchSms(params);
        System.out.println(o);
    }

    public void sendBatchSmsMd5(String phoneNo, String eg) {
        SubMailRequest params = new SubMailRequest();
        params.setContent(eg + "尊敬的客户，您正在进行下单，短信验证码是: @var(code) 。感谢您的使用，本条短信不用回复。");
        params.setMulti(test(phoneNo));
        params.setSignType(SignTypeEnum.MD5);
        Object o = subMailService.sendBatchSms(params);
        System.out.println(o);
    }

    public void sendBatchTemplateSms(String phoneNo, String templateId) {
        SubMailRequest params = new SubMailRequest();
        params.setMulti(test(phoneNo));
        params.setSignType(SignTypeEnum.NORMAL);
        Object o = subMailService.sendBatchTemplateSms(templateId, params);
        System.out.println(o);
    }

    public void sendBatchTemplateSmsMd5(String phoneNo, String templateId) {
        SubMailRequest params = new SubMailRequest();
        params.setMulti(test(phoneNo));
        params.setSignType(SignTypeEnum.MD5);
        Object o = subMailService.sendBatchTemplateSms(templateId, params);
        System.out.println(o);
    }

    private List<SubMailRequest.Multi> test(String phoneNo) {
        List<SubMailRequest.Multi> list = new ArrayList<>();
        SubMailRequest.Multi multi = new SubMailRequest.Multi();
        multi.setTo(phoneNo);
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 123456);
        multi.setVars(maps);
        list.add(multi);
        return list;
    }
}
