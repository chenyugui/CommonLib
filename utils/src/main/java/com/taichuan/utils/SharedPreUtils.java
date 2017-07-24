package com.taichuan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gui on 2017/7/24.
 * SharePreference工具类 <br>
 * 提示： <br>
 * Activity.getPreferences(int mode)生成Activity名.xml，用于Activity内部存储; <br>
 * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml; <br>
 * Context.getSharedPreference(String name,int mode)生成name.xml <br>
 */
public class SharedPreUtils {
    private static final SharedPreferences PREFERENCES = PreferenceManager.getDefaultSharedPreferences();


    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    private SharedPreUtils() {
    }

//    public void init(Context context, String spName) {
//        mContext = context.getApplicationContext();
//        mSpName = spName;
//    }


    @SuppressWarnings("unused")
    public static void saveParams(String key, String value) {
        getAppPreference()
                .edit()
                .putString(key, value)
                .apply();
    }

    @SuppressWarnings("unused")
    public void removeParams(String key) {
        getAppPreference()
                .edit()
                .remove(key)
                .apply();
    }

    @SuppressWarnings("unused")
    public String getParams(String key) {
        return getAppPreference().getString(key, "");
    }

    @SuppressWarnings("unused")
    public String getParams(String key, String defaultValue) {
        return getAppPreference().getString(key, defaultValue);
    }
}
