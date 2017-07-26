package com.cleverzheng.wallpaper.ui.test;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseActivity;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.http.api.PhotoService;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.wangzai.http.HttpClient;
import com.wangzai.http.rx.Transformer;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


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
                        .getSinglePhoto2("twukN12EN7c")
                        .compose(Transformer.<PhotoBean>transformer())
                        .subscribe(new Observer<PhotoBean>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull PhotoBean photoBeanResponse) {
                                String id = photoBeanResponse.getId();
                                LogUtil.i("onNext", id);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                LogUtil.i("error", e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
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
