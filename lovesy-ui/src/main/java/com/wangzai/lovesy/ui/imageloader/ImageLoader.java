package com.wangzai.lovesy.ui.imageloader;

import com.wangzai.lovesy.ui.widget.SimpleImageView;

/**
 * Created by wangzai on 2017/9/2
 */

public class ImageLoader {

    public static <T extends SimpleImageView> void loaderImage(T t, String imageUrl) {
        t.setImage(imageUrl);
    }

    public static <T extends SimpleImageView> void loaderBigImage(T t, String lowResUrl, String highResUrl) {
        t.setDetailImage(lowResUrl, highResUrl);
    }

//    public static <T extends SimpleImageView> void setRoundImage(T t, String imageUrl) {
//        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
//        roundingParams.setRoundAsCircle(true);
//        t.getHierarchy().setRoundingParams(roundingParams);
//
//        t.setImage(imageUrl);
//    }
//
//    public static <T extends SimpleImageView> void setDetailImage(T t, String lowResUrl, String highResUrl) {
//        t.setDetailImage(lowResUrl, highResUrl);
//    }
//
////    public static <T extends ZoomableImageView> void setZoomableImage(T t, String lowResUrl, String highResUrl) {
////        t.setImage(lowResUrl, highResUrl);
////    }
//
//    public static <T extends SimpleImageView> void setRoundedCornerImage(T t, String imageUrl, Context context) {
//        t.setRoundedCornerImage(imageUrl,context);
//    }
}
