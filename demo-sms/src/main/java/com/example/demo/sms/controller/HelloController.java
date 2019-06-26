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
 **/
@RestController
public class HelloController {

    /**
     * 1. 可以采用排除相关依赖的方式注入Service
     * 2. 可以采用加 @Qualifier("tencentSmsService")的方式注入Service ,
     *    value的可选值目前只有 tencentSmsService 和aliSmsService两种，
     * 3.  可以采用
     *      @Autowired
     *      private SmsService tencentSmsService;
     *      注入，方式与方法2类似
     * 采用方式1，最终的jar包将会比方式2和方法3小，但是最终只有一种短信模式
     * 生效，即只能使用一个短信运营商的服务，方式2，3能快速切换短信运营商
     */

    @Autowired
    private SmsService tencentSmsService;

    @Autowired
    private SmsService aliSmsService;

    @GetMapping("/tencent")
    public Object tencent() {
        // 具体配置请参照具体运营商
        // your template params
        String[] paramst = {"5678","5"};
        TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
        tencentSmsRequest.setPhoneNumber(new String[]{"your cellphone"});
        tencentSmsRequest.setParams(paramst);
        return tencentSmsService.sendTemplateSms("328921", tencentSmsRequest);
    }

    @GetMapping("/ali")
    public Object ali() {
        // 具体配置请参照具体运营商
        AliSmsRequest aliSmsRequest = new AliSmsRequest();
        aliSmsRequest.setOutId("420");
        aliSmsRequest.setPhoneNumbers(new String[]{"your cellphone"});
        aliSmsRequest.setTemplateParam("{\"code\":\"asdsads\"}");
        aliSmsRequest.setSignName("123");
        return aliSmsService.sendTemplateSms("328921",aliSmsRequest);
    }
}
