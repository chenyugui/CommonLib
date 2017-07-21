package com.taichuan.http.app;

import android.content.Context;

/**
 * Created by gui on 2017/7/21.
 */
public class HttpConfigurator {
    private static Context mContext;

    private HttpConfigurator() {
    }

    public static HttpConfigurator getInstance() {
        return Holder.INSTANCE;
    }

    public void putApplicationContext(Context context) {
        mContext = context;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    private static final class Holder {
        private static final HttpConfigurator INSTANCE = new HttpConfigurator();
    }
}
