package com.wangzai.lovesy.core.fragment.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.Route.Route;

/**
 * Created by wangzai on 2017/9/16
 */

public class WebViewClientImpl extends WebViewClient {
    private LoveSyActivity mActivity;

    private WebViewClientImpl(LoveSyActivity activity) {
        this.mActivity = activity;

    }

    public static WebViewClientImpl create(LoveSyActivity activity) {
        return new WebViewClientImpl(activity);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Route.getInstance().handleWebUrl(mActivity,url);
    }
}
