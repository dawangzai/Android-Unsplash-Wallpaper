package com.cleverzheng.wallpaper;

import android.app.Application;
import android.content.Context;

import com.cleverzheng.wallpaper.utils.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author：cleverzheng
 * @date：2017/2/4:15:40
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class WallpaperApplication extends Application {
    private static WallpaperApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        initLog();
        initFresco();
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
        LogUtil.initialize(BuildConfig.LOG_DEBUG);
    }
}
