package com.taichuan.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by gui on 17-7-15.
 * 单例模式OkHttpClient
 */
public class OkHttpClientCreator {
    private OkHttpClient okHttpClient;
    private static final int CONNECT_TIMEOUT_SECONDS = 5;

    private OkHttpClientCreator() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getInstance() {
        return OkHttpClientCreaterHolder.OK_HTTP_CLIENT_CREATOR.okHttpClient;
    }


    private static final class OkHttpClientCreaterHolder {
        private static final OkHttpClientCreator OK_HTTP_CLIENT_CREATOR = new OkHttpClientCreator();
    }
}
