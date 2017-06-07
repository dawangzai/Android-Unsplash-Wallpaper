package com.cleverzheng.wallpaper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.listener.OnNetworkErrorListener;
import com.cleverzheng.wallpaper.utils.LogUtil;

/**
 * Created by wangzai on 2017/2/18.
 */
public class BaseFragmentFragment extends Fragment implements BaseFragmentFunction {

    private View viewLoading;
    private View viewError;
    private TextView tvErrorMsg;

    private Context context;
    private Activity activity;

    @Override
    public void onAttach(Context context) {
        LogUtil.i(getTAG(), "------Fragment--onAttach------");
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.i(getTAG(), "------Fragment--onCreate------");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(getTAG(), "------Fragment--onCreateView------");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.i(getTAG(), "------Fragment--onActivityCreated------");
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 找状态控件
     */
    private void findStateView() {
        if (viewLoading == null || viewError == null) {
            viewLoading = getView().findViewById(R.id.viewLoading);
            viewError = getView().findViewById(R.id.viewError);
            if (viewLoading != null) {
                viewLoading.setVisibility(View.INVISIBLE);
            }
            if (viewError != null) {
                tvErrorMsg = (TextView) viewError.findViewById(R.id.tvErrorMsg);
                viewError.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onStart() {
        LogUtil.i(getTAG(), "------Fragment--onStart------");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.i(getTAG(), "------Fragment--onResume------");
        super.onResume();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 显示加载动画
     */
    protected void showLoadingView() {
        findStateView();
        if (getActivity() == null || viewLoading == null) {
            return;
        }
        hideErrorView();
        viewLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏加载动画
     */
    protected void hideLoadingView() {
        findStateView();
        if (getActivity() == null || viewLoading == null) {
            return;
        }
        viewLoading.setVisibility(View.INVISIBLE);
    }

    protected void showErrorView(String errorMsg, final OnNetworkErrorListener listener) {
        findStateView();
        if (getActivity() == null || viewError == null) {
            return;
        }
        tvErrorMsg.setText(errorMsg);
        viewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRetry();
            }
        });
        hideLoadingView();
        viewError.setVisibility(View.VISIBLE);
    }

    protected void hideErrorView() {
        findStateView();
        if (getActivity() == null || viewError == null) {
            return;
        }
        viewError.setVisibility(View.INVISIBLE);
    }

    public Context getFragmentContext() {
        if (context != null) {
            return context;
        } else {
            return null;
        }
    }

    protected String getTAG() {
        return Constant.TAG_LOG;
    }
}
