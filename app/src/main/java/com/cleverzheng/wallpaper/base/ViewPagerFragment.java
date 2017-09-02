package com.cleverzheng.wallpaper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by wangzai on 2017/5/22
 */

public class ViewPagerFragment extends BaseFragmentFragment {

    /**
     * 在onViewCreate方法中给它赋值
     */
    private View rootView;

    /**
     * 判断ViewPager中的某个Fragment是否已经对用户可见过了
     */
    private boolean isAlreadyVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (!isAlreadyVisible) {
            onFragmentVisibleChange(isVisibleToUser);
            isAlreadyVisible = true;
        }
    }

    /**
     * 在该方法中调用自定义的回调onFragmentVisibleChange()完成ViewPager中第一个Fragment用户可见时的回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (rootView == null) {
            rootView = view;
        }
        if (getUserVisibleHint() && !isAlreadyVisible) {
            onFragmentVisibleChange(getUserVisibleHint());
            isAlreadyVisible = true;
        }
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
    }
}
