package com.wangzai.lovesy.sign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wangzai.lovesy.core.fragment.web.IWebViewInitializer;
import com.wangzai.lovesy.core.fragment.web.Route.RouteKeys;
import com.wangzai.lovesy.core.fragment.web.WebFragment;
import com.wangzai.lovesy.core.fragment.web.WebViewInitializer;
import com.wangzai.lovesy.core.fragment.web.chromeclient.WebChromeClientImpl;
import com.wangzai.lovesy.core.fragment.web.client.WebViewClientImpl;

/**
 * Created by wangzai on 2017/9/18
 */

public class SignInFragment extends WebFragment implements IWebViewInitializer {

    public static SignInFragment create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (getUrl() != null) {
            SignInRoute.getInstance().loadWebPage(getWebView(), getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        return WebViewClientImpl.create(getLoveSyActivity(), SignInRoute.getInstance());
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }


}
