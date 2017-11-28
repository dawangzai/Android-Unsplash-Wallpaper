package com.wangzai.lovesy.ui.home.collection;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;

/**
 * Created by wangzai on 2017/9/21
 */

public class CollectionFragment extends BaseHomeFragment {
    @Override
    protected String setUrl() {
        return ApiService.Collections.COLLECTIONS_FEATURED;
//        return null;
    }

    @Override
    protected DataConverter setDataConverter() {
        return new CollectionDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new CollectionItemClickListener(mActivity);
    }
}
