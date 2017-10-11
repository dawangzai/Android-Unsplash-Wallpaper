package com.wangzai.lovesy.detail.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.imageloader.ImageLoader;
import com.wangzai.lovesy.core.widget.SimpleZoomableImageView;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/10/11
 */

public class PhotoActivity extends LoveSyActivity implements View.OnTouchListener, IImmersiveListener {

    @BindView(R.id.siv_photo)
    SimpleZoomableImageView mSivPhoto;
    @BindView(R.id.ll_photo)
    LinearLayout mLlPhoto;

    private View mDecorView;
    private GestureDetectorCompat mGestureDetector;

    @Override
    public int setLayout() {
        return R.layout.activity_photo;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        mDecorView = getWindow().getDecorView();
        mGestureDetector = new GestureDetectorCompat(this, new PhotoGestureListener(mDecorView, this));
        mLlPhoto.setOnTouchListener(this);
        final Bundle bundle = getIntent().getExtras();
        final String photoId = bundle.getString(Constant.INTENT_DATA.ONE);
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
                        final String full = urls.getFull();
                        ImageLoader.loaderZoomableImage(mSivPhoto, full);
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
        Toast.makeText(this, isImmerse + "", Toast.LENGTH_SHORT).show();
    }
}
