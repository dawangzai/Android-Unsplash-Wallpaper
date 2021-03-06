package com.wangzai.lovesy.core.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.wangzai.lovesy.core.R2;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/9/16
 */

public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    public abstract int setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState);

    @Nullable
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R2.id.fragment_container)
    FrameLayout fragmentContainer;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (setLayout() > 0) {
            setContentView(setLayout());
        } else {
            throw new
                    NullPointerException("type of setLayout() must be LayoutRes !");
        }

        ButterKnife.bind(this);
        initToolbar();
        onBindView(savedInstanceState);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void setTitle(@StringRes int title) {
        setTitle(getString(title));
    }

    public void setTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void loadRootFragment(@Nullable Bundle savedInstanceState, @IdRes int containerId, LoveSyFragment fragment) {
        if (fragmentContainer != null) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(containerId, fragment)
                        .commit();
            }
        }
    }

    public void loadFragment(LoveSyFragment fragment) {
        if (fragmentContainer != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer.getId(), fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
