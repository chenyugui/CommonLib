package com.taichuan.http;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * RESTful请求创建者
 */
public final class RestCreator<T> {
    private static String baseUrl;

    private RestCreator() {
    }

    public static RestService getRestService() {
        if (baseUrl == null) {
            throw new RuntimeException("baseUrl is null");
        }
        return RestServiceHolder.INSTANCE;
    }

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }


    private static final class RetrofitHolder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClientCreator.getInstance())// 自定义的OkHttpClient，不传的话则使用Retrofit内部默认的OkHttpClient
//                .addConverterFactory(GsonConverterFactory.create())// 传入Converter，才可将ResponseBody转化为其他类型
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService INSTANCE = RetrofitHolder.INSTANCE.create(RestService.class);
    }
}
