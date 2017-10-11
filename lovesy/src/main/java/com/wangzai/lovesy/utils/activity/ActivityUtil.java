/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wangzai.lovesy.utils.activity;

import android.animation.PropertyValuesHolder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.detail.photo.PhotoActivity;
import com.wangzai.lovesy.sign.SignInActivity;
import com.wangzai.lovesy.user.UserProfileActivity;

public class ActivityUtil {

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    public static void startSignInActivity(Activity activity) {
        Intent intent = new Intent(activity, SignInActivity.class);
        activity.startActivity(intent);
    }

    public static void startSignInActivityResult(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), SignInActivity.class);
        fragment.startActivityForResult(intent, Constant.REQUEST_CODE.PERSONAL_FRAGMENT);
    }

    public static void startUserProfileActivity(Activity activity) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        activity.startActivity(intent);
    }

    public static void startPhotoActivity(Activity activity, String photoId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_DATA.ONE, photoId);
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}
