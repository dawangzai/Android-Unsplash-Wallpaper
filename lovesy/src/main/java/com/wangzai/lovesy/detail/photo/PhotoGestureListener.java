package com.wangzai.lovesy.detail.photo;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangzai on 2017/10/11
 */

public class PhotoGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final View mDecorView;
    private final IImmersiveListener mListener;

    PhotoGestureListener(View decorView, IImmersiveListener listener) {
        this.mDecorView = decorView;
        this.mListener = listener;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (mDecorView.getSystemUiVisibility() == View.VISIBLE) {
            hideSystemUI();
            mListener.onImmerse(true);
        } else {
            showSystemUI();
            mListener.onImmerse(false);
        }
        return true;
    }

    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
