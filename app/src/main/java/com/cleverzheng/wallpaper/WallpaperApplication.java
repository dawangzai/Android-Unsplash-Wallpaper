package com.cleverzheng.wallpaper;

import android.app.Application;

import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by wangzai on 2017/2/4.
 */
public class WallpaperApplication extends Application {
    private static WallpaperApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        initLog();
        initFresco();
        initNetwork();
        initDownload();
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

    public static WallpaperApplication getInstance() {
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
