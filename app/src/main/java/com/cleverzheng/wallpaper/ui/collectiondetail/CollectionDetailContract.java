package com.cleverzheng.wallpaper.ui.collectiondetail;

import com.cleverzheng.wallpaper.base.BasePresenter;
import com.cleverzheng.wallpaper.base.BaseView;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

/**
 * Created by wangzai on 2017/7/10.
 */

public interface CollectionDetailContract {

    interface View extends BaseView<Presenter> {
        void setCollectionInfo(CollectionBean collectionBean);

        void refreshCollectionPhotos(List<PhotoBean> collectionBeanList);
    }

    interface Presenter extends BasePresenter {
        void getCollectionInfo(int collectionId);

        void getCollectionPhotos(int collectionId);
    }
}
