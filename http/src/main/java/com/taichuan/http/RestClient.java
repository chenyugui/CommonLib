package com.taichuan.http;

import android.util.Log;

import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IFailure;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;
import com.taichuan.http.callback.RequestCallbacks;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * RESTful请求客户端
 */
public final class RestClient {
    private static final String TAG = "RestClient";
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    RestClient(String url,
               WeakHashMap<String, Object> params,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    private void request(HttpMethod method) {
        if (REQUEST != null)
            REQUEST.onRequestStart();
        Call<String> call = null;
        switch (method) {
            case GET:
                call = RestCreator.getRestService().get(URL, PARAMS);
                break;
            case POST:
                call = RestCreator.getRestService().post(URL, PARAMS);
                break;
            case DELETE:
                call = RestCreator.getRestService().delete(URL, PARAMS);
                break;
        }
        if (call != null) {
            Log.d(TAG, "request: HttpMethod=" + method.name() + "\nURL=" + URL + "\nPARAMS=" + PARAMS.toString());
            call.enqueue(getRequestCallback());
        }
    }


    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

//    private void request(HttpMethod method) {
//        Call<String> call = null;
//
//        if (REQUEST != null) {
//            REQUEST.onRequestStart();
//        }
//
//        if (LOADER_STYLE != null) {
//            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
//        }
//
//        switch (method) {
//            case GET:
//                call = service.get(URL, PARAMS);
//                break;
//            case POST:
//                call = service.post(URL, PARAMS);
//                break;
//            case POST_RAW:
//                call = service.postRaw(URL, BODY);
//                break;
//            case PUT:
//                call = service.put(URL, PARAMS);
//                break;
//            case PUT_RAW:
//                call = service.putRaw(URL, BODY);
//                break;
//            case DELETE:
//                call = service.delete(URL, PARAMS);
//                break;
//            case UPLOAD:
//                final RequestBody requestBody =
//                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
//                final MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
//                call = service.upload(URL, body);
//                break;
//            default:
//                break;
//        }
//
//        if (call != null) {
//            call.enqueue(getRequestCallback());
//        }
//    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR
        );
    }

}
