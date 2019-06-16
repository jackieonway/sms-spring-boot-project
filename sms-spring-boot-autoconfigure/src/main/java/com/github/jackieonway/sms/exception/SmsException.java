package com.github.jackieonway.sms.exception;

/**
 * @author Jackie
 * @version \$id: SmsException.java v 0.1 2019-06-16 18:39 Jackie Exp $$
 */
public class SmsException extends RuntimeException {
    public SmsException() {
        super();
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super(cause);
    }

}
