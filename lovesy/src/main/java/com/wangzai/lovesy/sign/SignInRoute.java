package com.wangzai.lovesy.sign;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.BuildConfig;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.bean.Token;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.Route.BaseRoute;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.account.AccountManager;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/9/18
 */

class SignInRoute extends BaseRoute {

    private static final String SPLIT_CODE = "code=";
    private static final String AUTHORIZE = "authorize";
    private static final String LOGIN_URL = "https://unsplash.com/oauth/login";

    private SignInActivity mActivity;

    private SignInRoute() {
    }

    private static final class Holder {
        static final SignInRoute INSTANCE = new SignInRoute();
    }

    static SignInRoute getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean handleWebUrl(final LoveSyActivity activity, String url) {
        LogUtil.i(url);
        mActivity = (SignInActivity) activity;
        if (!url.contains(AUTHORIZE) && url.contains(BuildConfig.REDIRECT_URL)) {

            String[] split = url.split(SPLIT_CODE);
            LogUtil.i(split[1]);
            HashMap<String, Object> params = new HashMap<>(16);
            params.put("client_id", BuildConfig.CLIENT_ID);
            params.put("client_secret", BuildConfig.CLIENT_SECRET);
            params.put("redirect_uri", BuildConfig.REDIRECT_URL);
            params.put("code", split[1]);
            params.put("grant_type", "authorization_code");
            RxHttpClient.builder()
                    .url(ApiService.Oauth.OAUTH_TOKEN)
                    .loader(activity)
                    .params(params)
                    .build()
                    .post()
                    .subscribe(new ResultObserver() {
                        @Override
                        public void onSuccess(@NonNull String result) {
                            LogUtil.i(result);
                            signInSuccess(result);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            LogUtil.i(code + msg);
                        }
                    });
        } else if (url.equals(LOGIN_URL)) {
            return false;
        } else {
            activity.loadFragment(SignInFragment.create(url));
        }
        return true;
    }

    private void signInSuccess(String tokenString) {
        final Token token = JSON.parseObject(tokenString, Token.class);
        AccountManager.setSignState(true);
        AccountManager.setAccessToken(token.getAccess_token());
        if (mActivity != null) {
            mActivity.finish();
        }
    }
}
