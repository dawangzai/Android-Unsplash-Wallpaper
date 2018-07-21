package com.wangzai.lovesy;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.wangzai.lovesy.test.TestView;
import com.wangzai.lovesy.utils.LogUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/9/7
 */

public class HttpTestActivity extends AppCompatActivity {


    //    @BindView(R.id.pb_progress)
//    ProgressBar mPbProgress;
//    @BindView(R.id.btn_download)
//    Button mBtnDownload;
//    @BindView(R.id.btn_pause)
//    Button mBtnPause;
    @BindView(R.id.iv_test_photo)
    ImageView ivTestPhoto;
    @BindView(R.id.iv_test)
    TestView tvTest;

    private BitmapFactory.Options options;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        tvTest.setClickable(true);


//        options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.drawable.ic_bitmap_test, options);
//
//
//        final int reqHeight = ivTestPhoto.getHeight();
//        final int reqWidth = ivTestPhoto.getWidth();
//        LogUtil.i("reqWidth=" + reqWidth + "---reqHeight=" + reqHeight);
//        Matisse.from(this)
//                .choose(MimeType.ofAll(), false) // 选择 mime 的类型
//                .countable(true)
//                .maxSelectable(9) // 图片选择的最多数量
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f) // 缩略图的比例
//                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
//                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码


    }

    private int computerInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int outWidth = options.outWidth;
        final int outHeight = options.outHeight;
        int inSimpleSize = 1;

        if (outWidth >= reqWidth || outHeight >= reqHeight) {

        }
        return inSimpleSize;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
