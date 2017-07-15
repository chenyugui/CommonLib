package com.taichuan.http;

import okhttp3.OkHttpClient;

/**
 * Created by gui on 17-7-15.
 */
public class OkHttpClientCreater {
    private OkHttpClient okHttpClient;

    private OkHttpClientCreater() {
        okHttpClient = new OkHttpClient();
        okHttpClient.
    }

    public static OkHttpClient getInstance() {
        return OkHttpClientCreaterHolder.okHttpClientCreater.okHttpClient;
    }


    private static final class OkHttpClientCreaterHolder {
        private static final OkHttpClientCreater okHttpClientCreater = new OkHttpClientCreater();
    }
}
