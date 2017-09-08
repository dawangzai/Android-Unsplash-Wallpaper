package com.wangzai.lovesy;

import android.app.Application;

import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.net.interceptor.HeaderInterceptor;
import com.wangzai.lovesy.core.net.interceptor.InterceptorType;
import com.wangzai.lovesy.core.net.interceptor.Logger;
import com.wangzai.lovesy.global.Constant;
import com.wangzai.lovesy.http.HttpClient;
import com.wangzai.lovesy.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by wangzai on 2017/2/4
 */
public class LoveSyApp extends Application {
    private static LoveSyApp appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        initLog();
        initFresco();
        initNetwork();
        initDownload();

        LoveSy.init(this)
                .withLogEnable(BuildConfig.LOG_DEBUG, "LoveSyDebug")
                .withApiHost("https://api.unsplash.com/")
                .withInterceptor(new HeaderInterceptor(InterceptorType.INTERCEPTOR))
                .withInterceptor(new HttpLoggingInterceptor(new Logger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                .configure();
    }

    private void initNetwork() {
        com.wangzai.http.HttpClient.getInstance()
                .init(this)
                .setBaseUrl(BuildConfig.BASE_URL)
                .setHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID)
                .setCache()
                .setLog(BuildConfig.LOG_DEBUG, Constant.TAG_LOG)
                .setConnectTimeout(30)
                .setReadTimeout(30)
                .setWriteTimeout(30);
    }

    private void initDownload() {
        HttpClient.initDownloadEnvironment(this, 2);
    }

    public static LoveSyApp getInstance() {
        if (appContext != null) {
            return appContext;
        } else {
            return null;
        }
    }

    /**
     * 初始化图片加载器
     */
    private void initFresco() {
        Fresco.initialize(this);
    }

    /**
     * 初始化Log
     */
    private void initLog() {
        //初始化Log
        LogUtil.init(BuildConfig.LOG_DEBUG, Constant.TAG_LOG);
    }
}
