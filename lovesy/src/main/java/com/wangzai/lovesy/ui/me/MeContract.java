package com.wangzai.lovesy.ui.me;

import com.wangzai.lovesy.mvp.base.BasePresenter;
import com.wangzai.lovesy.mvp.base.BaseView;

/**
 * @author：cleverzheng
 * @date：2017/4/24:20:46
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MeContract {
    interface Presenter extends BasePresenter {
        /**
         * 登录
         */
        void login();

        /**
         * 查看下载
         */
        void checkDownload();

        /**
         * 查看收藏
         */
        void checkLike();
    }


    interface View extends BaseView<Presenter> {
        /**
         * 设置用户头像
         *
         * @param headUrl
         */
        void setUserHead(String headUrl);

        /**
         * 设置一些工具菜单
         */
        void setMenu();
    }
}


