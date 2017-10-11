package com.wangzai.lovesy.core.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.wangzai.lovesy.core.ui.image.zoomable.DoubleTapGestureListener;
import com.wangzai.lovesy.core.ui.image.zoomable.ZoomableDraweeView;
import com.wangzai.lovesy.core.util.StringUtil;

/**
 * Created by wangzai on 2017/10/11
 */

public class SimpleZoomableImageView extends ZoomableDraweeView {
    public SimpleZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        setTapListener(new DoubleTapGestureListener(this));
    }

    /**
     * 加载图片
     *
     * @param imageUrl
     */
    public void setImage(String imageUrl) {
        if (!StringUtil.isEmpty(imageUrl)) {
            Uri uri = Uri.parse(imageUrl);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
//                    .setImageRequest(ImageRequest.fromUri(highResUri))
                    .setUri(uri)
                    .setOldController(getController())
                    .build();
            setController(controller);
        }
    }
}
