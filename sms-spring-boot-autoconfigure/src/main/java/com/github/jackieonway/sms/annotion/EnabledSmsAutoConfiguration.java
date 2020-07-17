package com.github.jackieonway.sms.annotion;

import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * @author Jackie
 * @version \$id: EnabledSmsAutoConfiguration.java v 0.1 2019-05-11 10:02 Jackie Exp $$
 */
@EnableAsync
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnabledSmsAutoConfiguration {
}
