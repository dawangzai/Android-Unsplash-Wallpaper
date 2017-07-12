package com.cleverzheng.wallpaper.ui.collectiondetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseActivity;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/7/10.
 */

public class CollectionDetailActivity extends BaseActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initData() {
        super.initData();

        Intent intent = getIntent();
        int collectionId = intent.getIntExtra(Constant.Intent.INTENT_DATA_ONE, -1);

        CollectionDetailFragment collectionDetailFragment = (CollectionDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (collectionDetailFragment == null) {
            collectionDetailFragment = CollectionDetailFragment.getInstance();
        }
        addFragment(collectionDetailFragment, R.id.contentFrame);

        if (collectionId > 0) {
            new CollectionDetailPresenter(collectionDetailFragment, collectionId);
        }
    }
}
