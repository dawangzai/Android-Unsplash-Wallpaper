package com.wangzai.lovesy.core.net;

import android.content.Context;

import com.wangzai.lovesy.core.net.callback.IError;
import com.wangzai.lovesy.core.net.callback.IFailure;
import com.wangzai.lovesy.core.net.callback.IRequest;
import com.wangzai.lovesy.core.net.callback.ISuccess;

import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wangzai on 2017/8/18
 */

public class HttpClientBuilder {

    private static final HashMap<String, Object> PARAMS = HttpCreator.getParams();
    private String mUrl;
    private RequestBody mBody;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IError mError;
    private IFailure mFailure;
    private Context mContext;

    public final HttpClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final HttpClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final HttpClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final HttpClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final HttpClientBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final HttpClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final HttpClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public final HttpClientBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final HttpClient build() {
        return new HttpClient(mUrl,
                mBody,
                mRequest,
                mSuccess,
                mError,
                mFailure);
    }
}
