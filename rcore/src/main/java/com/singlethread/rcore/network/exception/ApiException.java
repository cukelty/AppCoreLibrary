package com.singlethread.rcore.network.exception;

/**
 * Created by litianyuan on 2017/8/30.
 */

public class ApiException extends RuntimeException{
    public final int errorCode;

    public ApiException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
