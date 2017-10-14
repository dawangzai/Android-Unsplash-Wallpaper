package com.wangzai.lovesy.view.widget;


import android.content.Context;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wangzai.lovesy.R;

/**
 * Created by wangzai on 2017/10/13
 */

public class RefreshView extends AppCompatImageView implements CollapsibleActionView {
    private Animation mAnimation;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.mipmap.ic_refresh);
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.widget_refresh_animation);
    }

    public void startRefresh() {
        startAnimation(mAnimation);
    }

    public void stopRefresh() {
        clearAnimation();
    }


    @Override
    public void onActionViewExpanded() {

    }

    @Override
    public void onActionViewCollapsed() {

    }
}
