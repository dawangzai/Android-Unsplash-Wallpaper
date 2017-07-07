package com.cleverzheng.wallpaper.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;

import butterknife.ButterKnife;


/**
 * Created by wangzai on 2017/7/7.
 */

public class TestFragment extends Fragment {
    public static TestFragment getInstance() {
        TestFragment testFragment = new TestFragment();
        return testFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
