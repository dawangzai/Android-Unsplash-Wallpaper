package com.wangzai.lovesy.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangzai.lovesy.R;

/**
 * Created by wangzai on 2018/2/8
 */

public class Fragment1 extends Fragment {

    public static Fragment1 getInstance() {
        return new Fragment1();
    }

    @Override
    public void onAttach(Context context) {
        Log.i("fragmentTest", "onAttach B");
        super.onAttach(context);
        Log.i("fragmentTest", "onAttach e");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("fragmentTest", "onCreate b");
        super.onCreate(savedInstanceState);
        Log.i("fragmentTest", "onCreate e");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("fragmentTest", "onCreateView");
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("fragmentTest", "onActivityCreated b");
        super.onActivityCreated(savedInstanceState);
        Log.i("fragmentTest", "onActivityCreated e");
    }

    @Override
    public void onStart() {
        Log.i("fragmentTest", "onStart b");
        super.onStart();
        Log.i("fragmentTest", "onStart e");
    }

    @Override
    public void onResume() {
        Log.i("fragmentTest", "onResume b");
        super.onResume();
        Log.i("fragmentTest", "onResume e");
    }

    @Override
    public void onPause() {
        Log.i("fragmentTest", "onPause b");
        super.onPause();
        Log.i("fragmentTest", "onPause e");
    }

    @Override
    public void onStop() {
        Log.i("fragmentTest", "onStop b");
        super.onStop();
        Log.i("fragmentTest", "onStop e");
    }

    @Override
    public void onDestroyView() {
        Log.i("fragmentTest", "onDestroyView b");
        super.onDestroyView();
        Log.i("fragmentTest", "onDestroyView e");
    }

    @Override
    public void onDestroy() {
        Log.i("fragmentTest", "onDestroy b");
        super.onDestroy();
        Log.i("fragmentTest", "onDestroy e");
    }

    @Override
    public void onDetach() {
        Log.i("fragmentTest", "onDetach b");
        super.onDetach();
        Log.i("fragmentTest", "onDetach e");
    }
}
