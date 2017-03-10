package com.cleverzheng.wallpaper.ui.persondetail;

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
 * @author：cleverzheng
 * @date：2017/2/24:22:09
 * @email：zhengwang043@gmail.com
 * @description：人物详情
 */

public class PersonDetailActivity extends BaseActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetail);
        ButterKnife.bind(this);
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
