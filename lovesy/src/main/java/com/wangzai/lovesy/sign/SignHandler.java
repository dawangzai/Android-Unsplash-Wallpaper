package com.wangzai.lovesy.sign;

import com.wangzai.lovesy.core.account.AccountManager;

/**
 * Created by wangzai on 2017/9/19
 */

class SignHandler {
    static void onSignIn(ISignListener signListener) {
        AccountManager.setSignState(true);
        if (signListener != null) {
            signListener.onSignInSuccess();
        }
    }
}
