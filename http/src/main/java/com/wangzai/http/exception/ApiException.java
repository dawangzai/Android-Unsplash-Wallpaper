package com.wangzai.http.exception;

/**
 * Created by wangzai on 2017/7/26.
 */

public class ApiException extends RuntimeException {
    public static final int UNKNOWN_CODE = 1000;
    public static final String UNKNOWN_EXCEPTION = "未知错误";

    public ApiException(int code, String message) {
        this(getExceptionMessage(code, message));
    }

    public ApiException(String message) {
        super(message);
    }

    private static String getExceptionMessage(int code, String message) {
        return code + "#" + message;
    }
}
