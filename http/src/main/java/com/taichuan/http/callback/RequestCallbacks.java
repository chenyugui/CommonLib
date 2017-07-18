package com.taichuan.http.callback;

import android.util.Log;

import com.taichuan.uilibrary.avloading.AVLoadingUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class RequestCallbacks implements Callback<String> {
    private static final String TAG = "RequestCallbacks";
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    /**
     * 请求时是否有伴随LoadingDialog
     */
    private final Boolean IS_WITH_LOADING;

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, boolean isWithLoading) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.IS_WITH_LOADING = isWithLoading;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.d(TAG, "onResponse: " + response);
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        requestFinish();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure(t);
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        requestFinish();
    }

    private void requestFinish() {
        if (IS_WITH_LOADING) {
            AVLoadingUtil.stopLoading();
        }
    }
}
