package com.cleverzheng.wallpaper.view.layout;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cleverzheng.wallpaper.R;

/**
 * @author：cleverzheng
 * @date：2017/2/26:10:14
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MyCoordinateLayout extends LinearLayout implements NestedScrollingParent {
    public MyCoordinateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        findViewById(R.id.)
    }
}
