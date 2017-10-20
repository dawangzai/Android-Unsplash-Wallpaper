package com.wangzai.lovesy.core.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;

/**
 * Created by wangzai on 2017/10/20
 */

public class NotificationBuilder {

    private int mId;
    @DrawableRes
    private int mSmallIcon;
    private String mTitle;
    private String mText;
    private Context mContext;
    private Class mResultActivity;
    private PendingIntent pendingIntent;

    public final NotificationBuilder id(int id) {
        this.mId = id;
        return this;
    }

    public final NotificationBuilder smallIcon(@DrawableRes int smallIcon) {
        this.mSmallIcon = smallIcon;
        return this;
    }

    public final NotificationBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public final NotificationBuilder text(String text) {
        this.mText = text;
        return this;
    }

    public final NotificationBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final NotificationBuilder resultActivity(Class resultActivity) {
        this.mResultActivity = resultActivity;
        return this;
    }

    public LoveSyNotification build() {
        if (mResultActivity != null) {
            Intent intent = new Intent(mContext, mResultActivity);
            pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return new LoveSyNotification(
                mId,
                mSmallIcon,
                mTitle,
                mText,
                mContext,
                mResultActivity,
                pendingIntent);
    }

}
