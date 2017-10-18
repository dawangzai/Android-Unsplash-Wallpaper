package com.wangzai.lovesy.home.index;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;

/**
 * Created by wangzai on 2017/9/21
 */

public class IndexFragment extends BaseHomeFragment {
    @Override
    protected String setUrl() {
//        return ApiService.Photos.PHOTOS;
        return null;
    }

    @Override
    protected DataConverter setDataConverter() {
        return new PhotoDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new IndexItemClickListener(this);
    }
}
