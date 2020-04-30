package com.example.demo.sms.controller;

import com.github.jackieonway.sms.entity.AliSmsRequest;
import com.github.jackieonway.sms.entity.TencentSmsRequest;
import com.github.jackieonway.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jackie
 * @version 1.0
 * @className HelloController
 * @description TODO
 * @date 2018/11/8 10:17
 * @since 0.0.2
 **/
@RestController
public class HelloController {

    /**
     * 相对于 0.0.1 版本的短信服务，从 0.0.2 开始,依赖取消了版本的强依赖性，
     * 这样就需要在使用的时候显示引入相关依赖
     * 使用注入 SmsService 时，直接注入极客
     * 现在支持 阿里、腾讯、云之讯短信服务
     */

    @Autowired
    private SmsService smsService;

    @GetMapping("/tencent")
    public Object tencent() {
        // 具体配置请参照具体运营商
        // your template params
        String[] paramst = {"5678","5"};
        TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
        tencentSmsRequest.setPhoneNumber(new String[]{"your cellphone"});
        tencentSmsRequest.setParams(paramst);
        return smsService.sendTemplateSms("328921", tencentSmsRequest);
    }

   /* @GetMapping("/ali")
    public Object ali() {
        // 具体配置请参照具体运营商
        AliSmsRequest aliSmsRequest = new AliSmsRequest();
        aliSmsRequest.setOutId("420");
        aliSmsRequest.setPhoneNumbers(new String[]{"your cellphone"});
        aliSmsRequest.setTemplateParam("{\"code\":\"asdsads\"}");
        aliSmsRequest.setSignName("123");
        return smsService.sendTemplateSms("328921",aliSmsRequest);
    }*/
}
