package com.taichuan.http;

import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IFailure;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * RESTful请求客户端Builder
 */
public final class RestClientBuilder {

    private WeakHashMap<String, Object> mParams;
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;

    public RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        mParams = params;
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }


    public final RestClient build() {
        return new RestClient(mUrl,
                mParams,
                mIRequest,
                mISuccess,
                mIFailure,
                mIError,
                mBody);
    }
}
