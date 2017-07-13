package com.cleverzheng.wallpaper.ui.persondetail;

import com.cleverzheng.wallpaper.base.BasePresenter;
import com.cleverzheng.wallpaper.base.BaseView;
import com.cleverzheng.wallpaper.bean.UserBean;

/**
 * @author：cleverzheng
 * @date：2017/2/24:22:19
 * @email：zhengwang043@gmail.com
 * @description：
 */

public interface PersonDetailContract {
    interface View extends BaseView<Presenter> {
        void setUserInfo(UserBean userBean); //设置用户信息
    }

    interface Presenter extends BasePresenter {
        void getUserInfo(); //获取用户信息

        void getPersonPhotos();

        void getPersonCollections();
    }
}
