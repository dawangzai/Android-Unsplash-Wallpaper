package com.wangzai.lovesy.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wangzai.lovesy.R;

/**
 * Created by wangzai on 2018/2/8
 */

public class TestFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("fragmentTest A", "onCreate b");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, Fragment1.getInstance(), "f1")
                .commit();

        Log.i("fragmentTest A", "onCreate e");
    }

    @Override
    protected void onStart() {
        Log.i("fragmentTest A", "onStart b");
        super.onStart();
        Log.i("fragmentTest A", "onStart e");
    }

    @Override
    protected void onResume() {
        Log.i("fragmentTest A", "onResume b");
        super.onResume();
        Log.i("fragmentTest A", "onResume e");
    }

    @Override
    protected void onPause() {
        Log.i("fragmentTest A", "onPause b");
        super.onPause();
        Log.i("fragmentTest A", "onPause e");
    }

    @Override
    protected void onStop() {
        Log.i("fragmentTest A", "onStop b");
        super.onStop();
        Log.i("fragmentTest A", "onStop e");
    }

    @Override
    protected void onDestroy() {
        Log.i("fragmentTest A", "onDestroy b");
        super.onDestroy();
        Log.i("fragmentTest A", "onDestroy e");
    }
}
