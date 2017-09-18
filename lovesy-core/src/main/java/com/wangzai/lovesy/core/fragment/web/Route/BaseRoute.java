package com.wangzai.lovesy.core.fragment.web.Route;

import android.webkit.WebView;

import com.wangzai.lovesy.core.activity.LoveSyActivity;

/**
 * Created by wangzai on 2017/9/18
 */

public abstract class BaseRoute {

    public abstract boolean handleWebUrl(LoveSyActivity activity, String url);

    public void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    public void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }
}
