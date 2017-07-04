package com.cleverzheng.wallpaper.ui.photodetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
