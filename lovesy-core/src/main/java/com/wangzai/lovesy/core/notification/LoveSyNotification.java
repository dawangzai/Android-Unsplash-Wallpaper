package com.wangzai.lovesy.core.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

import com.wangzai.lovesy.core.app.LoveSy;

/**
 * Created by wangzai on 2017/10/20
 */

public class LoveSyNotification {

    private final int mId;
    @DrawableRes
    private final int mSmallIcon;
    private final String mTitle;
    private final String mText;
    private final Context mContext;
    private final Class mResultActivity;
    private final PendingIntent mIntent;

    private static NotificationManager notificationManager =
            (NotificationManager) LoveSy.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    LoveSyNotification(int id,
                       int smallIcon,
                       String title,
                       String text,
                       Context context,
                       Class resultActivity,
                       PendingIntent intent) {
        this.mId = id;
        this.mSmallIcon = smallIcon;
        this.mTitle = title;
        this.mText = text;
        this.mContext = context;
        this.mResultActivity = resultActivity;
        this.mIntent = intent;
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public void notification() {
        final Notification build = new NotificationCompat.Builder(mContext)
                .setSmallIcon(mSmallIcon)
                .setContentTitle(mTitle)
                .setContentText(mText)
                .setContentIntent(mIntent)
                .build();
        notificationManager.notify(mId, build);
    }
}
