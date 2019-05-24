package com.example.demo.sms;

import com.pengzu.sms.annotion.EnabledPengzuSmsAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnabledPengzuSmsAutoConfiguration
@ComponentScan(basePackages = {"com.example.demo.sms","com.pengzu.sms"})
@SpringBootApplication
public class DemoSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSmsApplication.class, args);
    }

}
