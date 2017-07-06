package com.cleverzheng.wallpaper.operator;

import android.content.Context;

import com.cleverzheng.wallpaper.view.widget.DraweeImageView;
import com.cleverzheng.wallpaper.view.widget.ZoomableImageView;
import com.facebook.drawee.generic.RoundingParams;

/**
 * Created by wangzai on 2017/2/18.
 */
public class ImageLoaderOp {

    public static <T extends DraweeImageView> void setImage(T t, String imageUrl) {
        t.setImage(imageUrl);
    }

    public static <T extends DraweeImageView> void setRoundImage(T t, String imageUrl) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        t.getHierarchy().setRoundingParams(roundingParams);

        t.setImage(imageUrl);
    }

    public static <T extends DraweeImageView> void setDetailImage(T t, String lowResUrl, String highResUrl) {
        t.setDetailImage(lowResUrl, highResUrl);
    }

    public static <T extends ZoomableImageView> void setZoomableImage(T t, String lowResUrl, String highResUrl) {
        t.setImage(lowResUrl, highResUrl);
    }

    public static <T extends DraweeImageView> void setRoundedCornerImage(T t, String imageUrl, Context context) {
        t.setRoundedCornerImage(imageUrl,context);
    }
}
