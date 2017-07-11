package com.cleverzheng.wallpaper.ui.collection;

import com.cleverzheng.wallpaper.base.BasePresenter;
import com.cleverzheng.wallpaper.base.BaseView;
import com.cleverzheng.wallpaper.bean.CollectionBean;

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
