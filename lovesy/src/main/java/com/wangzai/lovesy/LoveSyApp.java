package com.wangzai.lovesy;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.icon.FontLoveSyModule;
import com.wangzai.lovesy.core.net.interceptor.HeaderInterceptor;
import com.wangzai.lovesy.core.net.interceptor.InterceptorType;
import com.wangzai.lovesy.core.net.interceptor.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by wangzai on 2017/2/4
 */
public class LoveSyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        LoveSy.init(this)
                .withLogEnable(BuildConfig.LOG_DEBUG, "LoveSyDebug")
                .withApiHost("https://api.unsplash.com/")
                .withIcon(new MaterialModule())
                .withIcon(new FontLoveSyModule())
                .withInterceptor(new HeaderInterceptor(InterceptorType.INTERCEPTOR))
                .withInterceptor(new HttpLoggingInterceptor(new Logger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                .configure();
    }

}
