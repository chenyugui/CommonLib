package com.taichuan.http;

import android.content.Context;
import android.util.Log;

import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IFailure;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;
import com.taichuan.http.callback.RequestCallbacks;
import com.taichuan.http.download.DownloadHandler;
import com.taichuan.uilibrary.avloading.AVLoadingUtil;
import com.taichuan.uilibrary.avloading.LoadingStyle;

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
    private final String DOWNLOAD_DIR;// 下载文件保存的文件夹
    private final String EXTENSION;// 下载文件的拓展名
    private final String NAME;// 下载文件保存的文件名（不包括路径）

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final boolean IS_SHOW_LOADING;
    private final Context CONTEXT;
    @SuppressWarnings("SpellCheckingInspection")
    private final RestClientBuilder.OnDownLoadProgress ONDOWNLOADPROGRESS;
    private final LoadingStyle LOADING_STYLE;
    private final Boolean LOADING_CANCELABLE;

    RestClient(String url,
               WeakHashMap<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               boolean isShowLoading,
               Context context,
               LoadingStyle loadingStyle,
               boolean isDialogCancelable,
               RestClientBuilder.OnDownLoadProgress onDownLoadProgress) {
        this.URL = url;
        this.PARAMS = params;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.IS_SHOW_LOADING = isShowLoading;
        this.CONTEXT = context;
        this.LOADING_STYLE = loadingStyle;
        this.LOADING_CANCELABLE = isDialogCancelable;
        this.ONDOWNLOADPROGRESS = onDownLoadProgress;
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
            case POST_RAW:
                call = RestCreator.getRestService().postRaw(URL, BODY);
                break;
            case DELETE:
                call = RestCreator.getRestService().delete(URL, PARAMS);
                break;
            case PUT:
                call = RestCreator.getRestService().put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = RestCreator.getRestService().putRaw(URL, BODY);
                break;
        }
        if (call != null) {
            // Loading Dialog
            if (IS_SHOW_LOADING && CONTEXT != null) {
                if (LOADING_STYLE == null) {
                    AVLoadingUtil.showLoading(CONTEXT, LOADING_CANCELABLE);
                } else {
                    AVLoadingUtil.showLoading(CONTEXT, LOADING_STYLE, LOADING_CANCELABLE);
                }
            }
            Log.d(TAG, "request: HttpMethod=" + method.name() + "\nURL=" + URL + "\nPARAMS=" + (PARAMS == null ? "null" : PARAMS.toString()));
            call.enqueue(getRequestCallback());
        }
    }


    @SuppressWarnings("unused")
    public final void get() {
        request(HttpMethod.GET);
    }

    @SuppressWarnings("unused")
    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    @SuppressWarnings("unused")
    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    /**
     * 下载文件<br>
     * 如果是下载APK，下载完毕后会打开请求安装
     */
    public final void download() {
        new DownloadHandler(URL, PARAMS, DOWNLOAD_DIR, EXTENSION, NAME, REQUEST,
                SUCCESS, FAILURE, ERROR, ONDOWNLOADPROGRESS)
                .handleDownload();
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                IS_SHOW_LOADING
        );
    }
}
