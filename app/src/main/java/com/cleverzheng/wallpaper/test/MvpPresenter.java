package com.cleverzheng.wallpaper.test;

/**
 * @author：cleverzheng
 * @date：2017/1/23:10:48
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MvpPresenter {
    MvpView mvpView;
    DataManager dataManager;

    public MvpPresenter(MvpView mvpView) {
        this.mvpView = mvpView;
        this.dataManager = new DataManager(new MvpModel() {
            @Override
            public String getStringFromRemote() {
                return null;
            }

            @Override
            public String getStringFromCache() {
                return null;
            }
        });
    }

    public void getString() {
        String text = dataManager.getShowContent();
        mvpView.showBtnText(text);
    }
}
