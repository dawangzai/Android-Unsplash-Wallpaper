package com.wangzai.lovesy.user.collection;

import android.os.Bundle;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.home.collection.CollectionDataConvert;

/**
 * Created by wangzai on 2017/10/11
 */

public class UserCollectionFragment extends BaseRefreshFragment {

    @Override
    protected String setUrl() {
        final Bundle bundle = getArguments();
        final String username = bundle.getString(Constant.BUNDLE.ONE);
        return "https://api.unsplash.com/users/" + username + "/collections";
    }

    @Override
    protected DataConverter setDataConverter() {
        return new CollectionDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new UserCollectionItemClickListener(this);
    }
}
