package com.wangzai.lovesy.sign;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.bean.Token;
import com.wangzai.lovesy.core.account.AccountManager;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.Route.BaseRoute;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.util.LogUtil;

import java.util.HashMap;
import java.util.WeakHashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/9/18
 */

class SignInRoute extends BaseRoute {

    private SignInActivity mActivity;

    private SignInRoute() {
    }

    private static final class Holder {
        static final SignInRoute INSTANCE = new SignInRoute();
    }

    public static SignInRoute getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean handleWebUrl(final LoveSyActivity activity, String url) {
        LogUtil.i(url);
        if (!url.contains("authorize") && url.contains("http://dawangzai.com")) {
            mActivity = (SignInActivity) activity;

            String[] split = url.split("code=");
            LogUtil.i(split[1]);
            HashMap<String, Object> params = new HashMap<>();
            params.put("client_id", "b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31");
            params.put("client_secret", "af3b7125ce78c9a05bac4f9b9f216260919c3646eaf02ea9f36f0f10b014a965");
            params.put("redirect_uri", "http://dawangzai.com");
            params.put("code", split[1]);
            params.put("grant_type", "authorization_code");
            RxHttpClient.builder()
                    .url("https://unsplash.com/oauth/token")
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
        }
//        else if (url.equals("https://unsplash.com/oauth/authorize?client_id=b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31&redirect_uri=http://dawangzai.com&response_type=code&scope=public+read_user+write_user+read_collections+write_collections")) {
//            LogUtil.i("到这了");
//        } else if (url.equals("https://unsplash.com/oauth/login")) {
//            mActivity.finish();
//        }
        else {
            activity.loadFragment(SignInFragment.create(url));
        }
        return false;
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
