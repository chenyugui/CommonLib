package com.taichuan.http;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gui on 2017/7/14.
 * ApiServiceCreater
 */
public class SampleApiServiceCreater {
    private String normalUrl = "127.0.0.1";

    private static Map<String, SampleApiService> map = new HashMap<>();

    private SampleApiServiceCreater() {
    }

    public static SampleApiServiceCreater get() {
        return SampleServiceHolder.SAMPLE_API_SERVICE_CREATER;
    }

    /**
     * 获取固定HostURL的ApiService
     */
    public SampleApiService getSampleApiService() {
        return getSampleApiService(normalUrl);
    }

    /**
     * 获取指定HostURL的ApiService
     */
    public SampleApiService getSampleApiService(String hostUrl) {
        if (map.containsKey(hostUrl)) {
            return map.get(hostUrl);
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(hostUrl)
                    .client(OkHttpClientCreater.getInstance())// 单例模式的OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SampleApiService sampleApiService = retrofit.create(SampleApiService.class);
            map.put(hostUrl, sampleApiService);
            return sampleApiService;
        }
    }

    /**
     * danli
     */
    private static final class SampleServiceHolder {
        private static final SampleApiServiceCreater SAMPLE_API_SERVICE_CREATER = new SampleApiServiceCreater();
    }
}
