package com.wangzai.lovesy.core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wangzai.lovesy.core.app.LoveSy;

/**
 * Created by wangzai on 2017/9/6
 */

public class DimenUtil {
    public static int getScreenWidth() {
        final Resources resources = LoveSy.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = LoveSy.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
