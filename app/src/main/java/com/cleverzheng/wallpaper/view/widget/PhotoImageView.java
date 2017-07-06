package com.cleverzheng.wallpaper.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.cleverzheng.wallpaper.R;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by wangzai on 2017/6/23.
 */

public class PhotoImageView extends PhotoDraweeView {
    public PhotoImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public PhotoImageView(Context context) {
        super(context);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setProgressBarImage(new ProgressBarDrawable())
//                .setPlaceholderImage(R.color.colorPrimaryDark)
                .build();
        setHierarchy(hierarchy);

    }

    public PhotoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setProgressBarImage(new ProgressBarDrawable())
//                .setPlaceholderImage(R.color.colorPrimaryDark)
                .build();
        setHierarchy(hierarchy);

    }
}
