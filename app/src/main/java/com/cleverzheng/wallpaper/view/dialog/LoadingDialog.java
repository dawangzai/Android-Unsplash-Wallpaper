package com.cleverzheng.wallpaper.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.cleverzheng.wallpaper.R;

/**
 * @author：cleverzheng
 * @date：2017/2/21:10:41
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.GuidDialogStyle);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }
}
