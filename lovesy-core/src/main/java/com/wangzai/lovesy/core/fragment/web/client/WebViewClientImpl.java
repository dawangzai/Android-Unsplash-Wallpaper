package com.wangzai.lovesy.core.fragment.web.client;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.fragment.web.Route.BaseRoute;
import com.wangzai.lovesy.core.ui.loader.LoveSyLoader;
import com.wangzai.lovesy.core.util.LogUtil;

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

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        LogUtil.i("onPageStarted" + url);
        LoveSyLoader.showLoading(mActivity);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.i("onPageFinished" + url);
        LoveSyLoader.stopLoading();
    }
}
