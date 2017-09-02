package com.wangzai.lovesy.view.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.utils.StringUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.wangzai.zoomable.zoomable.DoubleTapGestureListener;
import com.wangzai.zoomable.zoomable.ZoomableDraweeView;

/**
 * Created by wangzai on 2017/6/20.
 */

public class ZoomableImageView extends ZoomableDraweeView {
    public ZoomableImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        initView(context);
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
    }

    public ZoomableImageView(Context context) {
        super(context);
        initView(context);
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        setLayoutParams(layoutParams);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setProgressBarImage(new ProgressBarDrawable())
                .setPlaceholderImage(R.color.colorPrimaryDark)
                .build();
        setHierarchy(hierarchy);

        //允许缩放时切换
        setAllowTouchInterceptionWhileZoomed(true);
        //长按
        setIsLongpressEnabled(false);
        //双击击放大或缩小
        setTapListener(new DoubleTapGestureListener(this));
    }

    public void setImage(String lowResUrl, String highResUrl) {
        if (!StringUtil.isEmpty(lowResUrl) && !StringUtil.isEmpty(highResUrl)) {
            Uri lowResUri = Uri.parse(lowResUrl);
            Uri highResUri = Uri.parse(highResUrl);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                    .setImageRequest(ImageRequest.fromUri(highResUri))
                    .setOldController(getController())
                    .build();
            //加载图片
            setController(draweeController);
        }
    }
}
