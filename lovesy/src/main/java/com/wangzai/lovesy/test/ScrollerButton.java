package com.wangzai.lovesy.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.wangzai.lovesy.core.util.LogUtil;

/**
 * Created by wangzai on 2017/11/7
 */

public class ScrollerButton extends AppCompatTextView {

    private Scroller mScroller;
    private int mLastX = 0;
    private int mLastY = 0;

    public ScrollerButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mLastX = (int) getX();
        mLastY = (int) getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("getScrollX="+getScrollX());
                LogUtil.i("getScrollY="+getScrollY());
                mScroller.startScroll(mLastX, mLastY, (int) getTranslationX(), (int) getTranslationY(), 1000);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = (int) getX();
        mLastY = (int) getY();
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            LogUtil.i(mScroller.getCurrX() + "----" + mScroller.getCurrY());
            postInvalidate();
        }
    }
}
