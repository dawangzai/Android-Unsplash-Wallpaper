package com.wangzai.lovesy.home.collection;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;

/**
 * Created by wangzai on 2017/9/21
 */

public class CollectionFragment extends BaseRefreshFragment {
    @Override
    protected String setUrl() {
        return "collections/featured";
    }

    @Override
    protected DataConverter setDataConverter() {
        return new CollectionDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new CollectionItemClickListener();
    }
}
