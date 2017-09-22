package com.wangzai.lovesy.ui.newest;

import com.wangzai.lovesy.mvp.base.BasePresenter;
import com.wangzai.lovesy.mvp.base.BaseView;
import com.wangzai.lovesy.bean.PhotoBean;

import java.util.List;

/**
 * Created by wangzai on 2017/2/12.
 */

public interface NewestContract {

    interface View extends BaseView<Presenter> {
        void refresh(List<PhotoBean> photoList); //下拉刷新

        void loadMore(List<PhotoBean> photoList); //加载更多

        void clickPhotoDetail(String photoId);

        void clickUserDetail(String username);

        void downloadPicture(String url);
    }

    interface Presenter extends BasePresenter {
        void refreshData(int page, int per_page); //下拉刷新

        void loadMoreData(int page, int per_page); //加载更多

        void openPhotoDetail(String photoId);

        void openUserDetail(String username);

        void downloadPicture(String url);
    }
}
