package com.wangzai.lovesy.core.net.rx;

import android.content.Context;

import com.wangzai.lovesy.core.ui.loader.LoaderStyle;

import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wangzai on 2017/8/18
 */

public class RxHttpClientBuilder {

    private final WeakHashMap<String, Object> mParams = new WeakHashMap<>();
    private String mUrl;
    private String mDownloadDir;
    private String mDownloadName;
    private String mDownloadExtension;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;

    public final RxHttpClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxHttpClientBuilder downloadDir(String downloadDir) {
        this.mDownloadDir = downloadDir;
        return this;
    }

    public final RxHttpClientBuilder downloadName(String downloadName) {
        this.mDownloadName = downloadName;
        return this;
    }

    public final RxHttpClientBuilder downloadExtension(String downloadExtension) {
        this.mDownloadExtension = downloadExtension;
        return this;
    }

    public final RxHttpClientBuilder loader(Context context) {
        this.mContext = context;
        return this;
    }

    public final RxHttpClientBuilder loader(Context context, LoaderStyle style) {
        this.mLoaderStyle = style;
        return this;
    }

    public final RxHttpClientBuilder params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public final RxHttpClientBuilder params(WeakHashMap<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    public final RxHttpClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxHttpClient build() {
        return new RxHttpClient(mUrl,
                mParams,
                mDownloadDir,
                mDownloadName,
                mDownloadExtension,
                mBody,
                mContext,
                mLoaderStyle);
    }
}
