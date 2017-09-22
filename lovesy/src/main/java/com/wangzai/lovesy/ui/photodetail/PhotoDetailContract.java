package com.wangzai.lovesy.ui.photodetail;

import com.wangzai.lovesy.mvp.base.BasePresenter;
import com.wangzai.lovesy.mvp.base.BaseView;

/**
 * @author：cleverzheng
 * @date：2017/2/21:17:15
 * @email：zhengwang043@gmail.com
 * @description：
 */

public interface PhotoDetailContract {
    interface View extends BaseView<Presenter> {
        void setPhoto(String imageUrl);

        void setPhoto(String lowUrl, String highUrl);

        void setImageSize(int width, int height);
    }

    interface Presenter extends BasePresenter {
        void getSinglePhoto();

        void share(); //分享

        void download(); //下载

        void setWallpaper(); //设置壁纸
    }
}
