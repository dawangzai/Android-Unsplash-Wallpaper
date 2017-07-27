package com.cleverzheng.wallpaper.utils;

import android.util.Log;

/**
 * Created by wangzai on 2017/01/17.
 */
public class LogUtil {
    public static boolean DEBUG;
    public static String TAG;

    public static void init(boolean isDebug) {
        init(isDebug, "DebugLog");
    }

    public static void init(boolean isDebug, String tag) {
        LogUtil.DEBUG = isDebug;
        LogUtil.TAG = tag;
    }

    public static void v(String tag, String message) {
        if (DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void v(String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if (DEBUG) {
            Log.e(tag, message, e);
        }
    }

    public static void e(String message, Exception e) {
        if (DEBUG) {
            Log.e(TAG, message, e);
        }
    }
}
