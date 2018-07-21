package com.wangzai.lovesy.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.wangzai.lovesy.utils.LogUtil;

/**
 * Created by wangzai on 2018/1/16
 */

public class TestView extends AppCompatImageView {

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float x0 = event.getX(0);
            final float x1 = event.getX(1);
            final float y0 = event.getY(0);
            final float y1 = event.getY(1);
            Log.i("testLog","x0=" + x0 + "--x1=" + x1 + "--y0=" + y0 + "--y1=" + y1);
        }
        return super.onTouchEvent(event);
    }
}
