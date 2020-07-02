package com.github.jackieonway.sms.exception;

/**
 * @author Jackie
 * @version \$id: SmsException.java v 0.1 2019-06-16 18:39 Jackie Exp $$
 */
public class SmsException extends RuntimeException {
    private static final String SYS_TYPE_CONFIG_ERROR_MSG = "短信服务商信息配置错误";

    public SmsException() {
        super(SYS_TYPE_CONFIG_ERROR_MSG);
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super(SYS_TYPE_CONFIG_ERROR_MSG, cause);
    }

}
