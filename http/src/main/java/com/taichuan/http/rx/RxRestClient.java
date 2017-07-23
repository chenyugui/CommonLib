package com.taichuan.http.rx;

import java.util.WeakHashMap;

/**
 * Created by chenyugui on 2017/7/23.
 */
public class RxRestClient {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;


    public RxRestClient(String url, WeakHashMap<String, Object> params) {
        this.URL = url;
        this.PARAMS = params;
    }
}
