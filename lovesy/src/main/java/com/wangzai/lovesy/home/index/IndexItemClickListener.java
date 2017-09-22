package com.wangzai.lovesy.home.index;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.account.AccountManager;
import com.wangzai.lovesy.core.account.IUserChecker;

/**
 * Created by wangzai on 2017/9/22
 */

class IndexItemClickListener extends SimpleClickListener {

    private final IndexFragment mFragment;

    IndexItemClickListener(IndexFragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        final int id = view.getId();
        switch (id) {
            case R.id.siv_photo:
                Toast.makeText(mFragment.getContext(), "点击照片了~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.siv_avatar:
                Toast.makeText(mFragment.getContext(), "点击头像了~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_like:
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        Toast.makeText(mFragment.getContext(), "登录了~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNotSignIn() {
                        Toast.makeText(mFragment.getContext(), "请先登录~", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.iv_add_collect:
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        Toast.makeText(mFragment.getContext(), "登录了~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNotSignIn() {
                        Toast.makeText(mFragment.getContext(), "请先登录~", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
