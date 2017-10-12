package com.wangzai.lovesy.detail.photo;

import android.view.MotionEvent;
import android.view.View;

import com.wangzai.lovesy.core.ui.image.zoomable.DoubleTapGestureListener;
import com.wangzai.lovesy.core.ui.image.zoomable.ZoomableDraweeView;

/**
 * Created by wangzai on 2017/10/11
 */

public class PhotoGestureListener extends DoubleTapGestureListener {
    private final View mDecorView;
    private final IImmersiveListener mListener;
    private int mOptionShow = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
    private int mOptionHide = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;

    PhotoGestureListener(ZoomableDraweeView zoomableDraweeView, View decorView, IImmersiveListener listener) {
        super(zoomableDraweeView);
        this.mDecorView = decorView;
        this.mListener = listener;
        init();
    }

    private void init() {
        mDecorView.setSystemUiVisibility(mOptionShow);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if (mDecorView.getSystemUiVisibility() == mOptionShow) {
            hideSystemUI();
            mListener.onImmerse(true);
        } else {
            showSystemUI();
            mListener.onImmerse(false);
        }
        return true;
    }

    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(mOptionHide);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(mOptionShow);
    }
}
