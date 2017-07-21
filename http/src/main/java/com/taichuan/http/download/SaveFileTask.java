package com.taichuan.http.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.taichuan.http.callback.IRequest;
import com.taichuan.http.callback.ISuccess;
import com.taichuan.http.util.HttpConfigurator;
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
    private final IRequest REQUEST;

    SaveFileTask(ISuccess success, IRequest request) {
        this.SUCCESS = success;
        this.REQUEST = request;
    }

    @Override
    protected File doInBackground(Object... params) {
        ResponseBody body = (ResponseBody) params[0];
        String dir = (String) params[1];
        String extension = (String) params[2];
        String fileName = (String) params[3];
        Log.d(TAG, "doInBackground: " + body.contentLength());// 17635898
        InputStream is = body.byteStream();
        return FileUtil.writeToDisk(is, dir, extension, fileName, body.contentLength());
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        Log.d(TAG, "onPostExecute:" + file.length());
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            HttpConfigurator.getInstance().getApplicationContext().startActivity(install);
        }
    }
}
