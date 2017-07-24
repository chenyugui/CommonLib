package com.taichuan.http;

import android.content.Context;

import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IFailure;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;
import com.taichuan.uilibrary.avloading.AVLoadingUtil;
import com.taichuan.uilibrary.avloading.LoadingStyle;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * RESTful请求客户端Builder
 */
public final class RestClientBuilder {

    public interface OnDownLoadProgress {
        void onProgressUpdate(long fileLength, int downLoadedLength);
    }

    private String mUrl = null;
    private WeakHashMap<String, Object> mParams;
    private String mDownLoadDir;// 下载文件保存的文件夹
    private String mExtension;// 下载文件的拓展名
    private String mFileName;// 下载文件的全名（文件名+.+拓展名）
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private boolean isShowLoading = false;
    private Context mContext;
    private LoadingStyle mLoadingStyle = null;
    private OnDownLoadProgress mOnDownLoadProgress = null;
    private boolean mLoadingCancelable = AVLoadingUtil.default_cancelable;


    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    @SuppressWarnings("unused")
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

    /**
     * 下载文件要保存的目录
     */
    public final RestClientBuilder dir(String dir) {
        this.mDownLoadDir = dir;
        return this;
    }

    /**
     * 下载的文件的扩展名
     */
    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    /**
     * 下载的文件的文件名（名称+ . + 扩展名）。<br>
     * 此方法会覆盖掉 {@link #extension(String extension)}
     */
    public final RestClientBuilder fileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    /**
     * 传入原始数据
     *
     * @param raw 原始数据字符串
     */
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
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

    /**
     * 传入下载文件进度回调
     */
    public final RestClientBuilder onDownLoadProgress(OnDownLoadProgress onDownLoadProgress) {
        this.mOnDownLoadProgress = onDownLoadProgress;
        return this;
    }


    /**
     * 配置请求中的对话框
     */
    @SuppressWarnings("unused")
    public final RestClientBuilder loading(Context context, LoadingStyle loadingStyle, boolean cancelable) {
        this.isShowLoading = true;
        this.mContext = context;
        this.mLoadingStyle = loadingStyle;
        this.mLoadingCancelable = cancelable;
        return this;
    }

    /**
     * 配置请求中的对话框
     */
    @SuppressWarnings("unused")
    public final RestClientBuilder loading(Context context, LoadingStyle loadingStyle) {
        this.isShowLoading = true;
        this.mContext = context;
        this.mLoadingStyle = loadingStyle;
        return this;
    }

    /**
     * 配置请求中的对话框 （默认样式）
     */
    @SuppressWarnings("unused")
    public final RestClientBuilder loading(Context context, boolean cancelable) {
        this.mContext = context;
        this.mLoadingCancelable = cancelable;
        return this;
    }

    /**
     * 配置请求中的对话框 （默认样式）
     */
    @SuppressWarnings("unused")
    public final RestClientBuilder loading(Context context) {
        this.mContext = context;
        return this;
    }


    public final RestClient build() {
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }
        return new RestClient(mUrl,
                mParams,
                mDownLoadDir,
                mExtension,
                mFileName,
                mIRequest,
                mISuccess,
                mIFailure,
                mIError,
                mBody,
                isShowLoading,
                mContext,
                mLoadingStyle,
                mLoadingCancelable,
                mOnDownLoadProgress);
    }
}
