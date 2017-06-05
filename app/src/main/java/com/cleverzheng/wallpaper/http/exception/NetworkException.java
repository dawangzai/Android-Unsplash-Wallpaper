package com.cleverzheng.wallpaper.http.exception;

/**
 * Created by wangzai on 2017/5/26.
 */

public class NetworkException extends RuntimeException {

    public NetworkException(int code, String message) {
        this(getExceptionMessage(code, message));
    }

    public NetworkException(String apiExceptionMessage) {
        super(apiExceptionMessage);
    }

    private static String getExceptionMessage(int code, String message) {
        return code + "#" + message;
    }
}
