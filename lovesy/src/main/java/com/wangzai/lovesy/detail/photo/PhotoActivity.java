package com.wangzai.lovesy.detail.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.image.loader.ImageLoader;
import com.wangzai.lovesy.core.widget.SimpleZoomableImageView;
import com.wangzai.lovesy.view.widget.RefreshView;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/10/11
 */

public class PhotoActivity extends LoveSyActivity implements View.OnTouchListener, IImmersiveListener {

    @BindView(R.id.siv_photo)
    SimpleZoomableImageView mSivPhoto;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    private GestureDetectorCompat mGestureDetector;
    private Animation hideAnimation;
    private Animation showAnimation;
    private RefreshView mRefreshView;

    private String photoId;

    @Override
    public int setLayout() {
        return R.layout.activity_photo;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        final Bundle bundle = getIntent().getExtras();
        photoId = bundle.getString(Constant.BUNDLE.ONE);
        initTitle();
        View mDecorView = getWindow().getDecorView();
        mGestureDetector = new GestureDetectorCompat(this, new PhotoGestureListener(mSivPhoto, mDecorView, this));
        mSivPhoto.setTapListener(new PhotoGestureListener(mSivPhoto, mDecorView, this));

        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.widget_hide_animation);
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.widget_show_animation);

        getPhoto();
    }

    private void getPhoto() {
        RxHttpClient.builder()
                .url("photos/" + photoId)
                .loader(this)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@NonNull String result) {
                        final PhotoBean photoBean = JSON.parseObject(result, PhotoBean.class);
                        final UrlsBean urls = photoBean.getUrls();
                        final String regular = urls.getRegular();
                        final String full = urls.getFull();
                        ImageLoader.loaderZoomableImage(mSivPhoto, regular);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onImmerse(boolean isImmerse) {
        if (isImmerse) {
            mLlContainer.setVisibility(View.INVISIBLE);
            mLlContainer.startAnimation(showAnimation);
        } else {
            mLlContainer.setVisibility(View.VISIBLE);
            mLlContainer.startAnimation(hideAnimation);
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.detail_photo, menu);
//        final MenuItem item = menu.findItem(R.id.menu_refresh);
//        mRefreshView = (RefreshView) MenuItemCompat.getActionView(item);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//                mRefreshView.startRefresh();
////                getPhoto();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void initTitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
