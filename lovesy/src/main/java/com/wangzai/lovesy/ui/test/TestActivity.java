package com.wangzai.lovesy.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.base.BaseActivity;
import com.wangzai.lovesy.core.ui.refresh.RefreshHandler;


/**
 * Created by wangzai on 2017/5/22
 */

public class TestActivity extends BaseActivity {
    //        private String url = "http://img.mukewang.com/55249cf30001ae8a06000338.jpg";
    private String url = "https://images.unsplash.com/photo-1498477386155-805b90bf61f7?\n" +
            "ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=43bb5f269e7e388f8d8bf3b2d8065e73";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RefreshHandler refreshHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setProgressViewOffset(true, 120, 300);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, new TestDataConvert());

        refreshHandler.refresh();

    }

}
