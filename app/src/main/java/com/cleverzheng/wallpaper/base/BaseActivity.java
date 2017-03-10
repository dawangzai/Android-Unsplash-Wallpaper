package com.cleverzheng.wallpaper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.utils.ActivityUtil;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;
import com.cleverzheng.wallpaper.view.dialog.LoadingDialog;

/**
 * @author：cleverzheng
 * @date：2017/2/22:9:58
 * @email：zhengwang043@gmail.com
 * @description：Activity的基类
 */

public class BaseActivity extends AppCompatActivity implements BaseFunction {

    private static final String TAG = "cd";

    private boolean isFindTitle = false;
    private Toolbar toolbar;
    private TextView tvTitleName;

    private LoadingDialog loadingDialog;
    private boolean isExit = false;
    private long exitTime;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "------Activity--onCreate------");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        LogUtil.i(TAG, "------Activity--onStart------");
        super.onStart();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        LogUtil.i(TAG, "------Activity--onPostCreate------");
        super.onPostCreate(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        LogUtil.i(TAG, "------Activity--onResume------");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtil.i(TAG, "------Activity--onPause------");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtil.i(TAG, "------Activity--onDestroy------");
        super.onDestroy();
    }

    @Override
    public void initData() {

    }

    @Override
    public void showLoading() {
        if (this != null && !this.isFinishing()) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(BaseActivity.this);
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void dismissLoading() {
        if (this != null && !this.isFinishing()) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    @Override
    public void loadFailed() {

    }

    /**
     * 设置Title
     *
     * @param title
     */
    protected void setMainToolbar(String title) {
        findToolbarView();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
//        tvTitleName.setText(title);
    }

    protected void setCommonToolbar(String title) {
        findToolbarView();
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.ic_action_return);
        }
//        tvTitleName.setText(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showShort(this, "再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    /***
     * 添加Fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(Fragment fragment, int frameId) {
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), fragment, frameId);
    }

    private void findToolbarView() {
        if (!isFindTitle) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            tvTitleName = (TextView) findViewById(R.id.tvTitleName);
            isFindTitle = true;

            if (toolbar != null) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }
}
