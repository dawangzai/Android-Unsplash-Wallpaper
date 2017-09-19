package com.wangzai.lovesy.core.app;

import com.wangzai.lovesy.core.util.storage.LoveSyPreference;

/**
 * Created by wangzai on 2017/9/19
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 保存用户登录状态
     *
     * @param state 登录状态值
     */
    public static void setSignState(boolean state) {
        LoveSyPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 检查用户登录状态
     *
     * @param checker 回调
     */
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }

    private static boolean isSignIn() {
        return LoveSyPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

}
