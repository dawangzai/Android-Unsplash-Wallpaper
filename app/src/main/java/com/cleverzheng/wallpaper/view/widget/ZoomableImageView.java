package com.cleverzheng.wallpaper.view.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.cleverzheng.wallpaper.utils.StringUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.wangzai.zoomable.zoomable.DoubleTapGestureListener;
import com.wangzai.zoomable.zoomable.ZoomableDraweeView;

/**
 * Created by wangzai on 2017/6/20.
 */

public class ZoomableImageView extends ZoomableDraweeView {
    public ZoomableImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ZoomableImageView(Context context) {
        super(context);
        initView();
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //允许缩放时切换
        setAllowTouchInterceptionWhileZoomed(true);
        //长按
        setIsLongpressEnabled(false);
        //双击击放大或缩小
        setTapListener(new DoubleTapGestureListener(this));
    }

    public void setImage(String imageUrl) {
        if (!StringUtil.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);

            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .build();
            //加载图片
            setController(draweeController);
        }
    }
}
