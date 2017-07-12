package com.cleverzheng.wallpaper.listener;

import android.support.design.widget.AppBarLayout;

/**
 * Created by wangzai on 2017/7/7.
 */

public abstract class AppBarLayoutStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public static final int EXPANDED = 0;
    public static final int COLLAPSED = 1;
    public static final int IDLE = 2;

    private int mCurrentState = -1;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != EXPANDED) {
                onStateChanged(appBarLayout, EXPANDED);
            }
            mCurrentState = EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != COLLAPSED) {
                onStateChanged(appBarLayout, COLLAPSED);
            }
            mCurrentState = COLLAPSED;
        } else {
            if (mCurrentState != IDLE) {
                onStateChanged(appBarLayout, IDLE);
            }
            mCurrentState = IDLE;
        }

    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, int state);
}
