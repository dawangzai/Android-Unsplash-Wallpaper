package com.wangzai.lovesy.ui.collectiondetail;

import com.wangzai.lovesy.base.BasePresenter;
import com.wangzai.lovesy.base.BaseView;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.PhotoBean;

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
