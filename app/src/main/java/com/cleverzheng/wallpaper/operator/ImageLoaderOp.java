package com.cleverzheng.wallpaper.operator;

import com.cleverzheng.wallpaper.view.widget.MyDraweeView;
import com.facebook.drawee.generic.RoundingParams;

/**
 * @author：cleverzheng
 * @date：2017/2/18:8:51
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class ImageLoaderOp {

    public static <T extends MyDraweeView> void setImage(T t, String imageUrl) {
        t.setImage(imageUrl);
    }

    public static <T extends MyDraweeView> void setRoundImage(T t, String imageUrl) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        t.getHierarchy().setRoundingParams(roundingParams);

        t.setImage(imageUrl);
    }
}
