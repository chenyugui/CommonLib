package com.taichuan.http;


import retrofit2.Retrofit;

/**
 * Created by gui on 2017/7/14.
 */
public class SampleServiceCreater {
    private SampleApiService mSampleApiService;
    private

    private SampleServiceCreater() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl();
    }

    public static SampleApiService getSampleApiService(String url) {
        return SampleServiceHolder.getSampleApiService;
    }

    private static final class SampleServiceHolder {
        private static final SampleServiceCreater sampleServiceCreater = new SampleServiceCreater();
    }
}
