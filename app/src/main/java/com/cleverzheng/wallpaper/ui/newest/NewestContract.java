package com.cleverzheng.wallpaper.ui.newest;

import com.cleverzheng.wallpaper.base.BasePresenter;
import com.cleverzheng.wallpaper.base.BaseView;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/2/12:11:03
 * @email：zhengwang043@gmail.com
 * @description：最新页面的契约类管理View和Presenter
 */

public interface NewestContract {

    interface View extends BaseView<Presenter> {
        void refresh(List<PhotoBean> photoList); //下拉刷新

        void loadMore(List<PhotoBean> photoList); //加载更多

        void clickPhotoDetail(String photoId);

        void clickUserDetail(String username);
    }

    interface Presenter extends BasePresenter {
        void refreshData(int page, int per_page); //下拉刷新

        void loadMoreData(int page, int per_page); //加载更多

        void openPhotoDetail(String photoId);

        void openUserDetail(String username);
    }
}
