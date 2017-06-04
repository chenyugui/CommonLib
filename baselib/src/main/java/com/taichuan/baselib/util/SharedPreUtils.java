package com.taichuan.baselib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gui on 2016/7/6.
 * SharePreference工具类，请在application onCreate时调用init方法
 */
public class SharedPreUtils {
    private static String SPNAME;
    private static SharedPreUtils mSPContext;
    public Context mContext;


    public static SharedPreUtils getInstance() {

        if (mSPContext == null) {
            mSPContext = new SharedPreUtils();
        }
        return mSPContext;
    }

    private SharedPreUtils() {

    }

    private SharedPreUtils(Context context) {
        mContext = context;
        mSPContext = this;

    }

    public static void init(Context context, String spName) {
        mSPContext = new SharedPreUtils(context);
        SPNAME = spName;
    }

    public void savePamars(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public void removePamars(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }


    public String getPamars(String key) {

        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public String getPamars(String key, String defaultValue) {

        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);

        return sp.getString(key, defaultValue);
    }
}
