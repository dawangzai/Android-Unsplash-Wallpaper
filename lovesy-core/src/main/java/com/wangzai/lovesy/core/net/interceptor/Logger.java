package com.wangzai.lovesy.core.net.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by wangzai on 2017/8/23
 */

public class Logger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {

        Log.i("LoveSyDebug", message);
    }
}
