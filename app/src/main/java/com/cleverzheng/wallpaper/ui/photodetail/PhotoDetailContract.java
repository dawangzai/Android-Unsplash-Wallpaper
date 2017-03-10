package com.cleverzheng.wallpaper.ui.photodetail;

import com.cleverzheng.wallpaper.base.BasePresenter;
import com.cleverzheng.wallpaper.base.BaseView;

/**
 * @author：cleverzheng
 * @date：2017/2/21:17:15
 * @email：zhengwang043@gmail.com
 * @description：
 */

public interface PhotoDetailContract {
    interface View extends BaseView<Presenter> {
        void setPhoto(String photoUrl);
    }

    interface Presenter extends BasePresenter {
        void getSinglePhoto();

        void share(); //分享

        void download(); //下载

        void setWallpaper(); //设置壁纸
    }
}
