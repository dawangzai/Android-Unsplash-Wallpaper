package com.wangzai.lovesy.ui.me;

/**
 * @author：cleverzheng
 * @date：2017/4/24:20:45
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MePresenter implements MeContract.Presenter {
    private MeFragment mView;

    public MePresenter(MeFragment meFragment) {
        this.mView = meFragment;
        mView.setPresent(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login() {

    }

    @Override
    public void checkDownload() {

    }

    @Override
    public void checkLike() {

    }
}
