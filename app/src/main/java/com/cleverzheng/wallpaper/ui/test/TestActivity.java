package com.cleverzheng.wallpaper.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseActivity;


/**
 * Created by wangzai on 2017/5/22.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                LogUtil.i(getTAG(), "------d------");
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                LogUtil.i(getTAG(), "------onNext------" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.i(getTAG(), "------onError------");
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtil.i(getTAG(), "------onComplete------");
//            }
//        });
    }
}
