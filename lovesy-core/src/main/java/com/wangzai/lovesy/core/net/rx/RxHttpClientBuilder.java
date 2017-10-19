package com.wangzai.lovesy.core.net.rx;

import android.content.Context;

import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.ui.loader.LoaderStyle;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wangzai on 2017/8/18
 */

public class RxHttpClientBuilder {

    private static final HashMap<String, Object> PARAMS = HttpCreator.getParams();
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
        PARAMS.put(key, value);
        return this;
    }

    public final RxHttpClientBuilder params(HashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxHttpClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxHttpClient build() {
        return new RxHttpClient(mUrl,
                mDownloadDir,
                mDownloadName,
                mDownloadExtension,
                mBody,
                mContext,
                mLoaderStyle);
    }
}
