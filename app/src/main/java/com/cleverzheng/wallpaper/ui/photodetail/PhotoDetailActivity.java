package com.cleverzheng.wallpaper.ui.photodetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.FrameLayout;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseActivity;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/2/21.
 */
public class PhotoDetailActivity extends BaseActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);
        ButterKnife.bind(this);

//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setCommonToolbar("美图");
    }

    @Override
    public void initData() {
        super.initData();

        Intent intent = getIntent();
        String photoId = intent.getStringExtra(Constant.Intent.INTENT_DATA_ONE);

        PhotoDetailFragment photoDetailFragment = (PhotoDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (photoDetailFragment == null) {
            photoDetailFragment = photoDetailFragment.newInstance();
        }
        addFragment(photoDetailFragment, R.id.contentFrame);

        if (!StringUtil.isEmpty(photoId)) {
            new PhotoDetailPresenter(photoDetailFragment, photoId);
        }
    }
}
