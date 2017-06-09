package com.taichuan.baselib.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.taichuan.kangqiaolib.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gui on 2016/11/28.
 * 封装了运行时权限请求的Activity
 */
public class PermissionBaseActivity extends BaseActivity {
    private final String TAG = "PermissionBaseActivity";
    private Dialog permissionDialog;
    private Map<Integer, OnPermissionResultListener> listenerMap = new HashMap<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        OnPermissionResultListener onPermissionResultListener = listenerMap.get(requestCode);
        if (onPermissionResultListener != null) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listenerMap.remove(requestCode);
                onPermissionResultListener.onAllow();
            } else {
                listenerMap.remove(requestCode);
                onPermissionResultListener.onReject();
            }
        }
    }

    public void checkPermission(final String permission, String notifyMsg, OnPermissionResultListener onPermissionResultListener) {
        // 检查是否有权限
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            // 检查之前是否拒绝过该权限（不管是否设置了“不再提醒”，只要拒绝过，就会触发该方法）
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {//之前还没有拒绝过，直接申请权限
                int size = listenerMap.size();
                if (onPermissionResultListener != null) {
                    listenerMap.put(size, onPermissionResultListener);
                }
                ActivityCompat.requestPermissions(this, new String[]{permission}, size);
            } else { // 之前拒绝过该权限
                // 弹出自定义对话框，说明这里需要某某权限
                showPermissionDialog(this, notifyMsg, permission, onPermissionResultListener);
            }
        } else {
            if (onPermissionResultListener != null)
                onPermissionResultListener.onAllow();
        }
    }

    private void showPermissionDialog(Context context, String msg, final String permission, final OnPermissionResultListener onPermissionResultListener) {
        if (permissionDialog != null) {
            permissionDialog.cancel();
            permissionDialog = null;
        }
        permissionDialog = new Dialog(context, R.style.Dialog_No_Border);
        permissionDialog.setCanceledOnTouchOutside(false);
        View permissionDialogRootView = LayoutInflater.from(context).inflate(R.layout.dialog_nide_permission, null, true);
        permissionDialog.setContentView(permissionDialogRootView);
        permissionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                int size = listenerMap.size();
                if (onPermissionResultListener != null) {
                    listenerMap.put(size, onPermissionResultListener);
                }
                ActivityCompat.requestPermissions(PermissionBaseActivity.this, new String[]{permission}, size);
            }
        });
        TextView textView = (TextView) permissionDialogRootView.findViewById(R.id.tvNotify);
        textView.setText(msg);
        permissionDialogRootView.findViewById(R.id.rltOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.cancel();
            }
        });
        permissionDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (permissionDialog != null) {
            permissionDialog.cancel();
            permissionDialog = null;
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
