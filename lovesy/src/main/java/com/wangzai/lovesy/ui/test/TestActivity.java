package com.wangzai.lovesy.ui.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.base.BaseActivity;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.http.api.PhotoService;
import com.wangzai.lovesy.utils.LogUtil;
import com.wangzai.http.HttpClient;
import com.wangzai.http.observer.HttpObserver;
import com.wangzai.http.rx.Transformer;

import java.io.File;

import io.reactivex.disposables.Disposable;


/**
 * Created by wangzai on 2017/5/22.
 */

public class TestActivity extends BaseActivity {
    //        private String url = "http://img.mukewang.com/55249cf30001ae8a06000338.jpg";
    private String url = "https://images.unsplash.com/photo-1498477386155-805b90bf61f7?\n" +
            "ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=43bb5f269e7e388f8d8bf3b2d8065e73";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient
                        .createApi(PhotoService.class)
                        .getSinglePhoto("twukN12EN7c")
                        .compose(Transformer.<PhotoBean>transformer())
                        .subscribe(new HttpObserver<PhotoBean>() {
                            @Override
                            protected void getDisposable(Disposable d) {

                            }

                            @Override
                            protected void onError(int code, String errorMsg) {

                            }

                            @Override
                            protected void onSuccess(PhotoBean photoBean) {

                            }
                        });
            }
        });

        findViewById(R.id.btnTest2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File cacheDir = getCacheDir();
                File externalCacheDir = getExternalCacheDir();
                LogUtil.i("DebugMessage", externalStorageDirectory.toString());
                LogUtil.i("DebugMessage", cacheDir.toString());
                LogUtil.i("DebugMessage", externalCacheDir.toString());
            }
        });

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//
//        TestFragment testFragment = (TestFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.contentFrame);
//
//        if (testFragment == null) {
//            testFragment = TestFragment.getInstance();
//        }
//        addFragment(testFragment, R.id.contentFrame);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.navigation);


//        try {
//            OkHttpManger.getInstance().downloadAsync(url, Config.getDirFile("download").getAbsolutePath(), new OKHttpUICallback.ProgressCallback() {
//                @Override
//                public void onSuccess(Call call, Response response, String path) {
//                    Log.i("MainActivity","path:"+path);
//                }
//
//                @Override
//                public void onProgress(long byteReadOrWrite, long contentLength, boolean done) {
//                    Log.i("MainActivity","byteReadOrWrite:"+byteReadOrWrite+",contentLength:"+contentLength+",done:"+done);
//                }
//
//                @Override
//                public void onError(Call call, IOException e) {
//                    Log.i("MainActivity","onError");
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        DownloadManager.getInstances().downloadFile();
    }
}
