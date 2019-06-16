package com.example.demo.sms;

import com.github.jackieonway.sms.annotion.EnabledSmsAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnabledSmsAutoConfiguration
@SpringBootApplication
public class DemoSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSmsApplication.class, args);
    }

}
