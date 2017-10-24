package com.wangzai.lovesy.detail;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

/**
 * Created by wangzai on 2017/7/7
 */

public class AppBarLayoutStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private static final int EXPANDED = 0;
    private static final int COLLAPSED = 1;
    private static final int IDLE = 2;

    private int mCurrentState = -1;
    private CollapsingToolbarLayout mToolbarLayout;

    public AppBarLayoutStateChangeListener(CollapsingToolbarLayout toolbarLayout) {
        this.mToolbarLayout = toolbarLayout;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != EXPANDED) {
                mToolbarLayout.setTitleEnabled(false);
            }
            mCurrentState = EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != COLLAPSED) {
                mToolbarLayout.setTitleEnabled(true);
            }
            mCurrentState = COLLAPSED;
        } else {
            if (mCurrentState != IDLE) {
                mToolbarLayout.setTitleEnabled(false);
            }
            mCurrentState = IDLE;
        }
    }
}
