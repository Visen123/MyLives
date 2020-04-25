package com.app.lizhilives.utils;

/**
 * Created by Jackson Fu on 15/7/7.
 */
public class SystemException extends RuntimeException {

    public SystemException() {
    }

    public SystemException(String detailMessage) {
        super(detailMessage);
    }

    public SystemException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SystemException(Throwable throwable) {
        super(throwable);
    }
}
