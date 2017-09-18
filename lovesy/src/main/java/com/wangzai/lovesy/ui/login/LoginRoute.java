package com.wangzai.lovesy.ui.login;

import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.Route.BaseRoute;

/**
 * Created by wangzai on 2017/9/18
 */

class LoginRoute extends BaseRoute {

    private LoginRoute() {
    }

    private static final class Holder {
        static final LoginRoute INSTANCE = new LoginRoute();
    }

    public static LoginRoute getInstance() {
        return Holder.INSTANCE;
    }


    @Override
    public boolean handleWebUrl(LoveSyActivity activity, String url) {
        activity.loadFragment(LoginFragment.create(url));
        return true;
    }
}
