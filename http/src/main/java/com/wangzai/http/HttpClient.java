package com.wangzai.http;


import android.content.Context;
import android.util.Log;

import com.wangzai.http.interceptor.CacheInterceptor;
import com.wangzai.http.interceptor.HeaderInterceptor;
import com.wangzai.http.manager.OkHttpManager;
import com.wangzai.http.manager.RetrofitManager;
import com.wangzai.http.utils.LogUtil;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by wangzai on 2017/5/25.
 */

public class HttpClient {

    public static HttpClient instance;
    public static Context mContext;

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public HttpClient init(Context context) {
        this.mContext = context;
        return this;
    }

    public OkHttpClient.Builder getOkHttpBuild() {
        return OkHttpManager.getInstance().getBuilder();
    }

    public Retrofit.Builder getRetrofitBuild() {
        return RetrofitManager.getInstance().getRetrofitBuilder();
    }

    public HttpClient setBaseUrl(String baseUrl) {
        RetrofitManager.getInstance().setBaseUrl(baseUrl);
        return this;
    }

    public static <T> T createApi(Class<T> cls) {
        return RetrofitManager.createApi(cls);
    }

    /**
     * 设置读取超时时间
     *
     * @param second
     * @return
     */
    public HttpClient setReadTimeout(long second) {
        getOkHttpBuild().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param second
     * @return
     */
    public HttpClient setWriteTimeout(long second) {
        getOkHttpBuild().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置连接超时时间
     *
     * @param second
     * @return
     */
    public HttpClient setConnectTimeout(long second) {
        getOkHttpBuild().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置单个Header
     *
     * @param key
     * @param value
     * @return
     */
    public HttpClient setHeader(String key, String value) {
        getOkHttpBuild().addInterceptor(new HeaderInterceptor(key, value));
        return this;
    }

    /**
     * 设置多个Header
     *
     * @param header
     * @return
     */
    public HttpClient setHeader(Map<String, Object> header) {
        getOkHttpBuild().addInterceptor(new HeaderInterceptor(header));
        return this;
    }

    /**
     * 启用缓存默认路径，空间
     *
     * @return
     */
    public HttpClient setCache() {
        //缓存目录 Android\data\包名\cache\api_cache
        String cachePath = mContext.getExternalCacheDir() + "/api_cache";
        //缓存空间 10M
        int cacheSize = 10 * 1024 * 1024;
        setCache(cachePath, cacheSize);
        return this;
    }

    /**
     * 启用缓存
     *
     * @param cachePath 缓存路径
     * @param cacheSize 缓存大小
     * @return
     */
    public HttpClient setCache(String cachePath, int cacheSize) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        File cacheDir = new File(cachePath);
        Cache cache = new Cache(cacheDir, cacheSize);

        getOkHttpBuild()
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache);
        return this;
    }

    /**
     * Log输出
     *
     * @param isDebug 是否启用Log
     * @param tag     输出TAG
     * @return
     */
    public HttpClient setLog(boolean isDebug, String tag) {
        LogUtil.init(isDebug, tag);
        getOkHttpBuild().addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        return this;
    }
}