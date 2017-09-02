package com.wangzai.lovesy.operator;

import android.app.Activity;
import android.content.Intent;

import com.wangzai.lovesy.global.Constant;
import com.wangzai.lovesy.ui.collectiondetail.CollectionDetailActivity;
import com.wangzai.lovesy.ui.persondetail.PersonDetailActivity;
import com.wangzai.lovesy.ui.photodetail.PhotoDetailActivity;
import com.wangzai.lovesy.ui.test.TestActivity;

/**
 * @author：cleverzheng
 * @date：2017/2/22:14:01
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class OpenActivityOp {
    private OpenActivityOp() {
    }


    public static void openActivityStyle(Activity activity) {
    }

    public static void openPhotoDetailActivity(Activity activity, String photoId) {
        Intent intent = new Intent(activity, PhotoDetailActivity.class);
        intent.putExtra(Constant.Intent.INTENT_DATA_ONE, photoId);
        activity.startActivity(intent);
        openActivityStyle(activity);
    }

    public static void openPersonDetailActivity(Activity activity, String username) {
        Intent intent = new Intent(activity, PersonDetailActivity.class);
        intent.putExtra(Constant.Intent.INTENT_DATA_ONE, username);
        activity.startActivity(intent);
        openActivityStyle(activity);
    }

    public static void openTestActivity(Activity activity) {
        Intent intent = new Intent(activity, TestActivity.class);
        activity.startActivity(intent);
        openActivityStyle(activity);
    }

    public static void openCollectionDetailActivity(Activity activity, int collectionId) {
        Intent intent = new Intent(activity, CollectionDetailActivity.class);
        intent.putExtra(Constant.Intent.INTENT_DATA_ONE, collectionId);
        activity.startActivity(intent);
        openActivityStyle(activity);
    }
}
