package com.wangzai.lovesy.home.collection;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.account.AccountManager;
import com.wangzai.lovesy.account.IUserChecker;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.core.ui.recycler.MultipleFields;
import com.wangzai.lovesy.core.ui.recycler.MultipleItemEntity;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

/**
 * Created by wangzai on 2017/10/16
 */

public class CollectionItemClickListener extends SimpleClickListener {

    private final Activity mActivity;

    CollectionItemClickListener(Activity activity) {
        this.mActivity = activity;
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
        final String collection = entity.getField(MultipleFields.RAW_DATA);
        final int id = view.getId();
        switch (id) {
            case R.id.siv_photo:
                ActivityUtil.startCollectionDetailActivity(mActivity, collection);
                break;
            case R.id.siv_avatar:
                Toast.makeText(mActivity, mActivity.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
