package com.cleverzheng.wallpaper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.view.dialog.LoadingDialog;

/**
 * @author：cleverzheng
 * @date：2017/2/18:10:23
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class BaseFragment extends Fragment implements BaseFunction {

    private static final String TAG = "cd";

    private View contentView;
    private ViewGroup container;

    private LoadingDialog loadingDialog;

    private Context context;
    private Activity activity;

    private boolean isFindTitle = false;
    private Toolbar toolbar;
    private TextView tvTitleName;

    @Override
    public void onAttach(Context context) {
        LogUtil.i(TAG, "------Fragment--onAttach------");
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "------Fragment--onCreate------");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "------Fragment--onCreateView------");
        initData();
        initListener();
        this.container = container;
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "------Fragment--onActivityCreated------");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtil.i(TAG, "------Fragment--onStart------");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.i(TAG, "------Fragment--onResume------");
        super.onResume();
    }

    public void setContentView(int viewId) {
        this.contentView = getActivity().getLayoutInflater().inflate(viewId, container, false);
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void loadFailed() {

    }

    /**
     * 设置带返回键的Toolbar
     *
     * @param title
     */
    protected void setBackToolbar(String title) {
        findToolbarView();
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setNavigationIcon(R.mipmap.ic_action_return);
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }

    public Context getFragmentContext() {
        if (context != null) {
            return context;
        } else {
            return null;
        }
    }

    public Activity getFragmentActivity() {
        if (activity != null) {
            return activity;
        } else {
            return null;
        }
    }

    private void findToolbarView() {
        if (!isFindTitle) {
            toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
            tvTitleName = (TextView) contentView.findViewById(R.id.tvTitleName);
            isFindTitle = true;
        }
    }
}
