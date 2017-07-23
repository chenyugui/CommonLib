package com.taichuan.http.rx;

import java.util.WeakHashMap;

/**
 * Created by chenyugui on 2017/7/23.
 */
public class RxRestClientBuilder {
    private String mUrl;
    private WeakHashMap<String, Object> mParams;

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    @SuppressWarnings("unused")
    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        mParams = params;
        return this;
    }

    public RxRestClient build() {
        return new RxRestClient(mUrl, mParams);
    }

}
