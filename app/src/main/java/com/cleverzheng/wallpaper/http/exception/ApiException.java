package com.cleverzheng.wallpaper.http.exception;

/**
 * Created by wangzai on 2017/5/26.
 */

public class ApiException extends RuntimeException {
    public static final int Code_Default = 1003;

    public ApiException(int code, String message) {
        this(getApiExceptionMessage(code, message));
    }

    public ApiException(String apiExceptionMessage) {
        super(apiExceptionMessage);
    }

    private static String getApiExceptionMessage(int code, String message) {
        String msg;
        switch (code) {
            default:
                if ("".equals(message)) {
                    message = "网络错误";
                }
                msg = code + "#" + message;
        }
        return msg;
    }
}
