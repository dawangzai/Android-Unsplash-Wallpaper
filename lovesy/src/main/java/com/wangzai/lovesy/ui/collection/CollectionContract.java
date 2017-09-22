package com.wangzai.lovesy.ui.collection;

import com.wangzai.lovesy.mvp.base.BasePresenter;
import com.wangzai.lovesy.mvp.base.BaseView;
import com.wangzai.lovesy.bean.CollectionBean;

import java.util.List;

/**
 * Created by wangzai on 2017/2/21.
 */
public interface CollectionContract {
    interface View extends BaseView<Presenter> {
        void refresh(List<CollectionBean> collectionBeanList); //下拉刷新

        void loadMore(List<CollectionBean> collectionBeanList); //加载更多

        void clickCollectionDetail(int collectionId);
    }

    interface Presenter extends BasePresenter {
        void refreshData(int page, int per_page); //下拉刷新

        void loadMoreData(int page, int per_page); //加载更多
    }
}
