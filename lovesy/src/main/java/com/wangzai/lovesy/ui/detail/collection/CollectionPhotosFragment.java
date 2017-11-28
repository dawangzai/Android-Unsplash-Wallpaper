package com.wangzai.lovesy.ui.detail.collection;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.ui.home.index.PhotoDataConvert;

/**
 * Created by wangzai on 2017/10/24
 */

public class CollectionPhotosFragment extends BaseRefreshFragment {
    @Override
    protected String setUrl() {
        return ApiService.Photos.PHOTOS;
    }

    @Override
    protected DataConverter setDataConverter() {
        return new PhotoDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new CollectionDetailItemClickListener(this) ;
    }
}
