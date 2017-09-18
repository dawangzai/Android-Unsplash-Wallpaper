package com.wangzai.lovesy.core.fragment.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.Route.BaseRoute;

/**
 * Created by wangzai on 2017/9/16
 */

public class WebViewClientImpl extends WebViewClient {
    private LoveSyActivity mActivity;
    private BaseRoute mRoute;

    private WebViewClientImpl(LoveSyActivity activity, BaseRoute route) {
        this.mActivity = activity;
        this.mRoute = route;
    }

    public static WebViewClientImpl create(LoveSyActivity activity, BaseRoute route) {
        return new WebViewClientImpl(activity, route);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return mRoute.handleWebUrl(mActivity, url);
    }
}
