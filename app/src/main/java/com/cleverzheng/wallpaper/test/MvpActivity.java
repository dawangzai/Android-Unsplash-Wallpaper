package com.cleverzheng.wallpaper.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.cleverzheng.wallpaper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/1/22:15:35
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MvpActivity extends Activity implements MvpView {

    @BindView(R.id.btnHello)
    Button btnHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);

        loadData();
    }

    private void loadData() {
        MvpPresenter presenter = new MvpPresenter(this);
        presenter.getString();
    }

    @Override
    public void showBtnText(String text) {
        btnHello.setText(text);
    }
}
