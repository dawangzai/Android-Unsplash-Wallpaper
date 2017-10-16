package com.wangzai.lovesy.home.index;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.core.account.AccountManager;
import com.wangzai.lovesy.core.account.IUserChecker;
import com.wangzai.lovesy.core.ui.recycler.MultipleFields;
import com.wangzai.lovesy.core.ui.recycler.MultipleItemEntity;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

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
        MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
        final int id = view.getId();
        switch (id) {
            case R.id.siv_photo:
                ActivityUtil.startPhotoActivity(mFragment.getActivity(), (String) entity.getField(MultipleFields.ID));
                break;
            case R.id.siv_avatar:
                Toast.makeText(mFragment.getContext(), mFragment.getContext().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_like:
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        Toast.makeText(mFragment.getContext(), mFragment.getContext().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNotSignIn() {
                        Toast.makeText(mFragment.getContext(), mFragment.getContext().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.iv_add_collect:
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        Toast.makeText(mFragment.getContext(), mFragment.getContext().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNotSignIn() {
                        Toast.makeText(mFragment.getContext(), mFragment.getContext().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
