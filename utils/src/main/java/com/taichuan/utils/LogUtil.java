package com.taichuan.utils;

import android.util.Log;

/**
 * Created by gui on 2016/6/29.
 */
public class LogUtil {
    public static void v(String TAG, String msg) {
        if (isDebug()) {
            Log.v(TAG, msg);
        }

    }

    public static void d(String TAG, String msg) {
        if (isDebug()) {
            Log.d(TAG, msg);
        }

    }

    public static void e(String TAG, String msg) {
        if (isDebug()) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable e) {
        if (isDebug()) {
            Log.e(TAG, msg, e);
        }
    }

    public static void w(String TAG, String msg) {
        if (isDebug()) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String TAG, String msg, Throwable ex) {
        if (isDebug()) {
            Log.w(TAG, msg, ex);
        }
    }

    public static void i(String TAG, String msg) {
        if (isDebug()) {
            Log.i(TAG, msg);
        }
    }

    private static boolean isDebug() {
        return true;
    }
}
