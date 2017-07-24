package com.wangzai.http.exception;

/**
 * Created by wangzai on 2017/5/26.
 */

public class NetworkException extends RuntimeException {
    public static final int EXCEPTION_CODE_504 = 504;
    public static final String EXCEPTION_MESSAGE_504 = "请求失败，请稍后重试";

    public static final int EXCEPTION_CODE_10000 = 10000; //没有网络，没有缓存
    public static final int EXCEPTION_CODE_10001 = 10001; //网络不可用，没有缓存
    public static final int EXCEPTION_CODE_10002 = 10002; //网络不可用，不使用缓存
    public static final int EXCEPTION_CODE_10003 = 10003; //没有网络，不使用缓存
    public static final int EXCEPTION_CODE_UNKNOWN = 10000; //未知错误

    public static final String EXCEPTION_MESSAGE_UNKNOWN = "请求失败，请稍后重试";
    public static final String EXCEPTION_MESSAGE_10000 = "没有网络，请检查网络设置";
    public static final String EXCEPTION_MESSAGE_10001 = "网络不可用，请检查网络设置";
    public static final String EXCEPTION_MESSAGE_10002 = "网络不可用，请检查网络设置";
    public static final String EXCEPTION_MESSAGE_10003 = "没有网络，请检查网络设置";


    public NetworkException(int code, String message) {
        this(getExceptionMessage(code, message));
    }

    public NetworkException(String message) {
        super(message);
    }

    private static String getExceptionMessage(int code, String message) {
        return code + "#" + message;
    }
}
