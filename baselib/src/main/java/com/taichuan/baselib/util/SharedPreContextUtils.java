package com.taichuan.baselib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gui on 2016/7/6.
 */
public class SharedPreContextUtils {
    private static String SPNAME;
    private static SharedPreContextUtils mSPContext;
    public Context mContext;


    public static SharedPreContextUtils getInstance() {

        if (mSPContext == null) {
            mSPContext = new SharedPreContextUtils();
        }
        return mSPContext;
    }

    private SharedPreContextUtils() {

    }

    private SharedPreContextUtils(Context context) {
        mContext = context;
        mSPContext = this;

    }

    public static void init(Context context, String spName) {
        mSPContext = new SharedPreContextUtils(context);
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

    public void saveHids(String key, List<String> hids) {
        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (hids != null && hids.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hids.size(); i++) {
                stringBuilder.append(hids.get(i));
                if (i != hids.size() - 1) {
                    stringBuilder.append("&&");
                }
            }
            editor.putString(key, stringBuilder.toString());
        } else {
            editor.remove(key);
        }
        editor.apply();
    }

    public List<String> getHids(String key) {
        List<String> hids = new ArrayList<>();
        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        String strHids = sp.getString(key, "");
        if (!strHids.equals("")) {
            String[] ss = strHids.split("&&");
            Collections.addAll(hids, ss);
        }
        return hids;
    }

    public String getPamars(String key) {

        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        String strCom = sp.getString(key, "");

        return strCom;
    }

    public String getPamars(String key, String defaultValue) {

        SharedPreferences sp = mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        String strCom = sp.getString(key, defaultValue);

        return strCom;
    }
}
