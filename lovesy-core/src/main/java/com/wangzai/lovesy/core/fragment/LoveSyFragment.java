package com.wangzai.lovesy.core.fragment;

import com.wangzai.lovesy.core.activity.LoveSyActivity;

/**
 * Created by wangzai on 2017/9/16
 */

public abstract class LoveSyFragment extends BaseFragment {

    protected LoveSyActivity getLoveSyActivity() {
        return (LoveSyActivity) getActivity();
    }
}
