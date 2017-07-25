package com.wangzai.http.manager;

import android.os.Environment;

import com.wangzai.http.interceptor.CacheInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by wangzai on 2017/7/25.
 */

public class OkHttpManager {
    public static OkHttpManager instance;
    private OkHttpClient.Builder builder;

    private OkHttpManager() {
        this.builder = new OkHttpClient.Builder();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public void setCache() {
        //缓存目录 \data\data\包名\cache\api_cache
        String cachePath = Environment.getExternalStorageDirectory() + "api_cache";
        //缓存空间 10M
        int cacheSize = 10 * 1024 * 1024;
        setCache(cachePath, cacheSize);
    }

    public void setCache(String cachePath, int cacheSize) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        File cacheDir = new File(cachePath);
        Cache cache = new Cache(cacheDir, cacheSize);
        getBuilder()
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache);
    }
}
