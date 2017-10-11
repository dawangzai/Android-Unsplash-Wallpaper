package com.wangzai.lovesy.core.ui.imageloader;

import com.wangzai.lovesy.core.widget.SimpleImageView;
import com.wangzai.lovesy.core.widget.SimpleZoomableImageView;

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

    public static <T extends SimpleZoomableImageView> void loaderZoomableImage(T t, String imageUrl) {
        t.setImage(imageUrl);
    }

}
