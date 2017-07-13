package com.cleverzheng.wallpaper.view.layout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * @author：cleverzheng
 * @date：2017/2/17:14:27
 * @email：zhengwang043@gmail.com
 * @description：下拉刷新的控件
 */
public class MyRefreshLayout extends SwipeRefreshLayout {

    public MyRefreshLayout(Context context) {
        super(context);
        initRefreshLayout(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRefreshLayout(context);
    }

    /**
     * 给RefreshLayout设置一些初始值
     */
    private void initRefreshLayout(Context context) {

//        setProgressViewOffset(true, 50, 200);
        setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//        setProgressBackgroundColor(android.R.color.white);
    }
}
