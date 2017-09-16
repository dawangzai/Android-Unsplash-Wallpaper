package com.wangzai.lovesy.core.fragment.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangzai.lovesy.core.fragment.web.Route.RouteKeys;

/**
 * Created by wangzai on 2017/9/16
 */

public class SignFragment extends WebFragment {

    public static SignFragment create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final SignFragment fragment = new SignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return null;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public IWebViewInitializer setInitializer() {
        return null;
    }
}
