package com.cleverzheng.wallpaper.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Random;

/**
 * @author：cleverzheng
 * @date：2017/2/17:15:55
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MyDraweeView extends SimpleDraweeView {

    public MyDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        initDraweeView();
    }

    public MyDraweeView(Context context) {
        super(context);
        initDraweeView();
    }

    public MyDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDraweeView();
    }

    public MyDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDraweeView();
    }

    public MyDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDraweeView();
    }

    private void initDraweeView() {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setPlaceholderImage(R.color.text_nav)
                .build();
        setHierarchy(hierarchy);
    }

    /**
     * 加载图片
     *
     * @param imageUrl
     */
    public void setImage(String imageUrl) {
        if (!StringUtil.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);
            setImageURI(uri);
        }
    }
}
