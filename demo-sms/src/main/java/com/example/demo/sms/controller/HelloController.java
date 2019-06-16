package com.example.demo.sms.controller;

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
 **/
@RestController
public class HelloController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/sayHello")
    public Object sayHello() {
        // your template params
        String[] paramst = {"5678","5"};
        TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
        tencentSmsRequest.setPhoneNumber(new String[]{"your cellphone"});
        tencentSmsRequest.setParams(paramst);
        return smsService.sendTemplateSms("328921", tencentSmsRequest);
    }
}
