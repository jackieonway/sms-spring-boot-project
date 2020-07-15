package com.github.jackieonway.sms.submail.exception;

/**
 * @author bing_huang
 * @since V1.0 2020/07/09 7:59
 */
public class SubMailException extends RuntimeException {
    public SubMailException() {
    }

    public SubMailException(String message) {
        super(message);
    }

    public SubMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
