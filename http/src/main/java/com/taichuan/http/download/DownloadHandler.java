package com.taichuan.http.download;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.taichuan.http.HttpMethod;
import com.taichuan.http.RestClientBuilder;
import com.taichuan.http.RestCreator;
import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IFailure;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gui on 2017/7/21.
 * 处理下载文件的类
 */
public class DownloadHandler {
    private static final String TAG = "DownloadHandler";
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final String DIR;// 保存文件的文件夹的目录
    private final String EXTENSION;// 文件的拓展名
    private final String NAME;// 文件全名
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    @SuppressWarnings("SpellCheckingInspection")
    private final RestClientBuilder.OnDownLoadProgress ONDOWNLOADPROGRESS;

    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           String dir,
                           String extension,
                           String name,
                           IRequest request,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           RestClientBuilder.OnDownLoadProgress onDownLoadProgress) {
        this.URL = url;
        this.PARAMS = params;
        this.DIR = dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.ONDOWNLOADPROGRESS = onDownLoadProgress;
    }

    public void handleDownload() {
        Call<ResponseBody> call = RestCreator.getRestService().download(URL, PARAMS);
        if (call != null) {
            if (REQUEST != null) {
                REQUEST.onRequestStart();
            }
            Log.d(TAG, "request: HttpMethod=" + HttpMethod.DOWNLOAD.name() + "\nURL=" + URL + "\nPARAMS=" + (PARAMS == null ? "null" : PARAMS.toString()));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        SaveFileTask saveFileTask = new SaveFileTask(SUCCESS, ERROR, REQUEST, ONDOWNLOADPROGRESS);
                        saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, body, DIR, EXTENSION, NAME);
                        // 这里一定要注意判断，否则文件下载不全
                        // 因为SaveFileTask.executeOnExecutor方法是异步的，SaveFileTask在这里被cancel了，说明文件肯定下载不全
                        if (saveFileTask.isCancelled()) {
                            Log.w(TAG, "onResponse: saveFileTask.isCancelled");
                            if (REQUEST != null) {
                                REQUEST.onRequestEnd();
                            }
                            if (ERROR != null) {
                                ERROR.onError(-1, "SaveFileTask isCancel");
                            }
                        }
                    } else {
                        if (ERROR != null) {
                            ERROR.onError(response.code(), response.message());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    if (FAILURE != null) {
                        FAILURE.onFailure(t);
                    }
                }
            });
        } else {
            throw new RuntimeException("Call<ResponseBody> is null!");
        }

    }
}
