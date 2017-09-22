package com.wangzai.lovesy.ui.persondetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.mvp.base.BaseActivity;
import com.wangzai.lovesy.mvp.global.Constant;
import com.wangzai.lovesy.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/2/24.
 */

public class PersonDetailActivity extends BaseActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetail);
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
        String username = intent.getStringExtra(Constant.Intent.INTENT_DATA_ONE);

        PersonDetailFragment personDetailFragment = (PersonDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (personDetailFragment == null) {
            personDetailFragment = PersonDetailFragment.getInstance();
        }
        addFragment(personDetailFragment, R.id.contentFrame);

        if (!StringUtil.isEmpty(username)) {
            new PersonDetailPresenter(this, personDetailFragment, username);
        }
    }
}
