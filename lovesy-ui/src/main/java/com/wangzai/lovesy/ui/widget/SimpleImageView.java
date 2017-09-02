package com.wangzai.lovesy.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.wangzai.lovesy.util.StringUtil;

/**
 * Created by wangzai on 2017/9/2
 */
public class SimpleImageView extends SimpleDraweeView {

//    public SimpleImageView(Context context, GenericDraweeHierarchy hierarchy) {
//        super(context, hierarchy);
//        initImageView();
//    }

    public SimpleImageView(Context context) {
        super(context);
        initImageView();
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageView();
    }

    private void initImageView() {
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy hierarchy = builder
////                .setPlaceholderImage()
//                .build();
//        setHierarchy(hierarchy);
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

    /**
     * 查看大图
     *
     * @param lowResUrl  用于预览的低分辨率图片地址
     * @param highResUrl 要显示的高分辨率图片地址
     */
    public void setDetailImage(String lowResUrl, String highResUrl) {
        if (!StringUtil.isEmpty(lowResUrl) && !StringUtil.isEmpty(highResUrl)) {
            Uri lowResUri = Uri.parse(lowResUrl);
            Uri highResUri = Uri.parse(highResUrl);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                    .setImageRequest(ImageRequest.fromUri(highResUri))
                    .setOldController(getController())
                    .build();
            setController(controller);
        }
    }

    public void setRoundedCornerImage(String imageUrl, Context context) {
        if (!StringUtil.isEmpty(imageUrl)) {

//            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
//            GenericDraweeHierarchy hierarchy = builder
//                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER)
//                    .build();
//            setHierarchy(hierarchy);

            RoundingParams roundingParams = RoundingParams.fromCornersRadius(15f);
            // alternatively use fromCornersRadii or asCircle
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                roundingParams.setOverlayColor(context.getColor(R.color.color_55000000));
            }
            GenericDraweeHierarchy hierarchy = getHierarchy();
            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
            hierarchy.setRoundingParams(roundingParams);
            setImage(imageUrl);
        }
    }
}
