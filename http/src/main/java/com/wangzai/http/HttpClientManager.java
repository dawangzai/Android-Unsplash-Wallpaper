package com.wangzai.http;

import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;

/**
 * Created by wangzai on 2017/7/4.
 */

public class HttpClientManager {
    private static HttpClientManager mInstance;

    private OkHttpClient mOkHttpClient;
    private OkHttpClient mNoCacheOKHttpClient;
    private OkHttpClient mCacheShortOKHttpClient;
    private OkHttpClient mCacheMidOKHttpClient;
    private OkHttpClient mCacheLongOKHttpClient;

    private static final ReentrantLock LOCK = new ReentrantLock();

    private HttpClientManager() {
    }

    public static HttpClientManager getInstance() {
        try {
            LOCK.lock();
            if (null == mInstance) {
                mInstance = new HttpClientManager();
            }
        } finally {
            LOCK.unlock();
        }

        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder();

            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    public OkHttpClient getOkHttpClient(int cacheType) {
        OkHttpClient okHttpClient = null;

//        if (cacheType == CacheType.CACHE_TYPE_DEFAULT) {
//            okHttpClient = getNoCacheOkHttpClient();
//        } else if (cacheType == CacheType.CACHE_TYPE_SHORT) {
//            okHttpClient = getCacheShortOkHttpClient();
//        } else if (cacheType == CacheType.CACHE_TYPE_MID) {
//            okHttpClient = getCacheMidOkHttpClient();
//        } else if (cacheType == CacheType.CACHE_TYPE_LONG) {
//            okHttpClient = getCacheLongOkHttpClient();
//        } else {
//            okHttpClient = getNoCacheOkHttpClient();
//        }

        return okHttpClient;
    }

    private synchronized OkHttpClient getNoCacheOkHttpClient() {
//        if (mNoCacheOKHttpClient == null) {
//            EasyCacheInterceptor interceptor = new EasyCacheInterceptor(EasyCacheType.CACHE_TYPE_DEFAULT);
//
//            OkHttpClient.Builder builder = new OkHttpClient()
//                    .newBuilder()
//                    .addNetworkInterceptor(interceptor);
//            if (!TextUtils.isEmpty(mConfig.getUserAgent())) {
//                builder.addNetworkInterceptor(new EasyUserAgentInterceptor(mConfig.getUserAgent()));
//            }
//            if (mDebug) {
//                builder.addNetworkInterceptor(new EasyLoggingInterceptor());
//            }
//
//            mNoCacheOKHttpClient = builder.build();
//        }

        return mNoCacheOKHttpClient;
    }


}
