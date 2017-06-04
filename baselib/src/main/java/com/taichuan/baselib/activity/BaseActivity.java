package com.taichuan.baselib.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taichuan.baselib.R;


/**
 * Created by gui on 2017/5/21.
 * activity基类，实现一些最通用的方法
 */
public class BaseActivity extends PermissionBaseActivity {
    // TAG有长度限制，23
    protected final String TAG = getClass().getSimpleName().replace("Activity", "Aty");
    private Dialog tipDialog;
    private Toast mToast;
    protected BaseActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    /**
     * 显示提示对话框
     *
     * @param tipMsg             要提示的内容
     * @param isFinishWhenCancel 当对话框消失时，Activity是否进行finish
     */
    public void showTipDialog(String tipMsg, final boolean isFinishWhenCancel) {
        if (tipDialog == null) {
            tipDialog = new Dialog(this, R.style.Dialog_No_Border);
            tipDialog.setContentView(R.layout.dialog_tip);
            tipDialog.setCanceledOnTouchOutside(false);
            tipDialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tipDialog.cancel();
                }
            });
        }
        tipDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (isFinishWhenCancel) {
                    finish();
                }
            }
        });
        TextView tv_tip = (TextView) tipDialog.findViewById(R.id.tv_tip);
        tv_tip.setText(tipMsg);
        if (!this.isFinishing())
            tipDialog.show();
    }

    public void showShort(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLong(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        mToast.show();
    }
}
