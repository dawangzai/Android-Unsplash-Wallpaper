package com.wangzai.lovesy.ui.detail.photo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.ui.image.loader.ImageLoader;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.core.widget.SimpleZoomableImageView;
import com.wangzai.lovesy.view.widget.RefreshView;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/11
 */

public class PhotoActivity extends LoveSyActivity implements View.OnTouchListener, IImmersiveListener, ValueAnimator.AnimatorUpdateListener {

    @BindView(R.id.siv_photo)
    SimpleZoomableImageView mSivPhoto;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.rl_background)
    RelativeLayout mRlBackground;

    private GestureDetectorCompat mGestureDetector;
    private Animation hideAnimation;
    private Animation showAnimation;
    private ObjectAnimator hideAnimator;
    private ObjectAnimator showAnimator;
    private ValueAnimator valueAnimator;
    ViewGroup.MarginLayoutParams layoutParams;
    private RefreshView mRefreshView;

    private String photoId;

    @Override
    public int setLayout() {
        return R.layout.activity_photo;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        initTitle();
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            photoId = bundle.getString(Constant.BUNDLE.ONE);
        }
        View mDecorView = getWindow().getDecorView();
        mGestureDetector = new GestureDetectorCompat(this, new PhotoGestureListener(mSivPhoto, mDecorView, this));
        mSivPhoto.setTapListener(new PhotoGestureListener(mSivPhoto, mDecorView, this));

        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.widget_hide_animation);
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.widget_show_animation);

        final float y = mLlContainer.getY();
        final int top = mLlContainer.getTop();
        final int height = mLlContainer.getHeight();

        layoutParams = (ViewGroup.MarginLayoutParams) mLlContainer.getLayoutParams();
        LogUtil.i("Height=" + layoutParams.height + "--Width=" + layoutParams.width);
        LogUtil.i("Y=" + y + "--Top=" + top + "--Height=" + height);
        hideAnimator = ObjectAnimator.ofFloat(mLlContainer,
                "Y",
                60)
                .setDuration(3000);
        showAnimator = ObjectAnimator.ofFloat(mLlContainer,
                "Y",
                -60)
                .setDuration(3000);

        valueAnimator = ValueAnimator.ofInt(0, layoutParams.height);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(this);

        getPhoto();
    }

    private void getPhoto() {
//        RxHttpClient.builder()
//                .url(ApiService.Photos.PHOTOS + photoId)
//                .loader(this)
//                .build()
//                .get()
//                .subscribe(new ResultObserver() {
//                    @Override
//                    public void onSuccess(@NonNull String result) {
//                        final PhotoBean photoBean = JSON.parseObject(result, PhotoBean.class);
//                        final UrlsBean urls = photoBean.getUrls();
//                        final String regular = urls.getRegular();
//                        final String full = urls.getFull();
//                        ImageLoader.loaderZoomableImage(mSivPhoto, regular);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                    }
//                });
        String imageUrl = "https://images.unsplash.com/photo-1510083108436-e79e71336425?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=08f1125b9441ee6e992de89cb47074e3";
        ImageLoader.loaderZoomableImage(mSivPhoto, imageUrl);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onImmerse(boolean isImmerse) {
        if (isImmerse) {
//            mLlContainer.setVisibility(View.INVISIBLE);
//            mLlContainer.startAnimation(showAnimation);
//            hideAnimator.start();
            valueAnimator.start();
            mRlBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_black));
        } else {
//            mLlContainer.setVisibility(View.VISIBLE);
//            mLlContainer.startAnimation(hideAnimation);
//            showAnimator.start();
            valueAnimator.start();
            mRlBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_white));
        }
    }

    private void initTitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        final int animatedValue = (int) valueAnimator.getAnimatedValue();
        layoutParams.bottomMargin += -animatedValue;
        mLlContainer.requestLayout();
    }
}
