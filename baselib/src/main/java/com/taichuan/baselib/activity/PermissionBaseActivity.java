package com.taichuan.baselib.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.taichuan.baselib.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gui on 2016/11/28.
 * 封装了运行时权限请求的Activity
 */
public class PermissionBaseActivity extends AutoLayoutActivity {
    private final String TAG = "PermissionBaseActivity";
    private Dialog tipDialog;
    private Map<Integer, OnPermissionResultListener> listenerMap = new HashMap<>();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult  permissions.length=" + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult  grantResults.length=" + grantResults.length);
        OnPermissionResultListener onPermissionResultListener = listenerMap.get(requestCode);
        // 循环判断权限，只要有一个拒绝了，则回调onReject()。 全部允许时才回调onAllow()
        for (int i = 0; i < grantResults.length; i++) {
            listenerMap.remove(requestCode);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {// 拒绝权限
                // 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                // 注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 "不再提醒" 选项，此方法将返回 false。
                // 如果用户第一次申请权限，此方法返回false
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    // 拒绝选了"不再提醒"，一般提示跳转到权限设置页面
                }
                showTipDialog(permissions[i], onPermissionResultListener);
                return;
            }
        }
        onPermissionResultListener.onAllow();
    }

    public void checkPermissions(final String[] permissions, OnPermissionResultListener onPermissionResultListener) {
        if (Build.VERSION.SDK_INT < 23 || permissions.length == 0) {
            if (onPermissionResultListener != null)
                onPermissionResultListener.onAllow();
        } else {
            int size = listenerMap.size();
            if (onPermissionResultListener != null) {
                listenerMap.put(size, onPermissionResultListener);
            }
            ActivityCompat.requestPermissions(this, permissions, size);
        }
    }

    private void showTipDialog(String permisssion, final OnPermissionResultListener onPermissionResultListener) {
        if (tipDialog == null) {
            tipDialog = new Dialog(this, R.style.Dialog_No_Border);
            View permissionDialogRootView = LayoutInflater.from(this).inflate(R.layout.dialog_nide_permission, null, true);
            // 按钮
            permissionDialogRootView.findViewById(R.id.rltOK).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tipDialog.cancel();
                }
            });
            //
            tipDialog.setContentView(permissionDialogRootView);
        }
        tipDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onPermissionResultListener.onReject();
            }
        });
        TextView textView = (TextView) tipDialog.findViewById(R.id.tvNotify);
        textView.setText(getTip(permisssion));
        tipDialog.show();
    }

    private String getTip(String permission) {
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) || permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return "这里需要存储权限才可使用";
        } else if (permission.equals(Manifest.permission.CAMERA)) {
            return "这里需要相机权限才可使用";
        } else if (permission.equals(Manifest.permission.CALL_PHONE)) {
            return "这里需要使用电话权限才可使用";
        } else if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
            return "这里需要麦克风权限才可使用";
        } else if (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return "这里需要获取位置权限才可使用";
        } else {
            return "这里需要权限才可使用";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tipDialog != null) {
            tipDialog.cancel();
            tipDialog = null;
        }
        listenerMap.clear();
        listenerMap = null;
    }

    /**
     * 权限请求结果监听者
     */
    public interface OnPermissionResultListener {
        /**
         * 权限被允许
         */
        void onAllow();

        /**
         * 权限被拒绝
         */
        void onReject();
    }
}
