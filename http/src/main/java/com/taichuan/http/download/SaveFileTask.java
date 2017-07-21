package com.taichuan.http.download;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.taichuan.http.RestClientBuilder;
import com.taichuan.http.app.HttpConfigurator;
import com.taichuan.http.callback.IError;
import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;
import com.taichuan.utils.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by gui on 2017/7/21.
 * 把ResponseBody保存到本地的异步类
 */
final class SaveFileTask extends AsyncTask<Object, Integer, File> {
    private static final String TAG = "SaveFileTask";
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IRequest REQUEST;
    @SuppressWarnings("SpellCheckingInspection")
    private final RestClientBuilder.OnDownLoadProgress ONDOWNLOADPROGRESS;
    private long fileLength;// 文件的大小

    SaveFileTask(ISuccess success, IError error, IRequest request, RestClientBuilder.OnDownLoadProgress onDownLoadProgress) {
        this.SUCCESS = success;
        this.ERROR = error;
        this.REQUEST = request;
        this.ONDOWNLOADPROGRESS = onDownLoadProgress;
    }

    @Override
    protected File doInBackground(Object... params) {
        ResponseBody body = (ResponseBody) params[0];
        String dir = (String) params[1];
        String extension = (String) params[2];
        String fileName = (String) params[3];
        Log.d(TAG, "doInBackground: " + body.contentLength());// 17635898
        InputStream is = body.byteStream();
        FileUtil.WriteToDiskCallBack writeToDiskCallBack = new FileUtil.WriteToDiskCallBack() {
            @Override
            public void onProgressUpdate(int progress) {// 子线程
                SaveFileTask.this.onProgressUpdate(progress);
            }
        };
        fileLength = body.contentLength();
        return FileUtil.writeToDisk(is, dir, extension, fileName, fileLength, writeToDiskCallBack);
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (ONDOWNLOADPROGRESS != null) {
            ONDOWNLOADPROGRESS.onProgressUpdate(fileLength, values[0]);
        }
    }

    /**
     * @param file 已下载的文件
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        Log.d(TAG, "onPostExecute:" + file.length());
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        if (file.length() == fileLength) {// 文件下载齐全
            if (SUCCESS != null) {
                SUCCESS.onSuccess(file.getPath());
            }
            // 如果是APK文件，跳转到安装APK
            if (FileUtil.isApk(file)) {
                Context context = HttpConfigurator.getInstance().getApplicationContext();
                if (context != null) {
                    // 老师这里安装APK没有判断android版本，7.0要另外处理
                    FileUtil.installApk(
                            file,
                            context,
                            context.getPackageName() + ".fileprovider");
                } else {
                    throw new RuntimeException("context is null,cannot install apk");
                }
            }
        } else {// 文件下载不全
            if (ERROR != null) {
                ERROR.onError(-1, "file length err");
            }
        }
    }
}
