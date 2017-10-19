package com.wangzai.lovesy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wangzai.lovesy.core.ui.refresh.RefreshHandler;

/**
 * Created by wangzai on 2017/9/7
 */

public class HttpTestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RefreshHandler refreshHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test);

//        @SuppressWarnings("unchecked") HttpObserver observer = new HttpObserver(new OnResultCallback() {
//            @Override
//            public void onSuccess(Object o) {
//
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//
//            }
//        });
//        HttpClient.getApplicationContext().getSinglePhoto(observer, "pvZrGsVbLd8");

        setContentView(R.layout.activity_test);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setProgressViewOffset(true, 120, 300);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, new TestDataConvert());
//
//        refreshHandler.refresh();
    }
}
