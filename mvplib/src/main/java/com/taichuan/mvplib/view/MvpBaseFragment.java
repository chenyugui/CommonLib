package com.taichuan.mvplib.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taichuan.mvplib.R;
import com.taichuan.mvplib.presenter.MvpBasePresenter;
import com.taichuan.mvplib.view.viewimpl.ViewBaseInterface;

/**
 * Created by gui on 2017/5/28.
 * fragment View层基类
 */
public abstract class MvpBaseFragment<V extends ViewBaseInterface, P extends MvpBasePresenter> extends Fragment implements ViewBaseInterface {
    // Presenter对象
    protected P mPresenter;
    private Dialog tipDialog;
    private Toast mToast;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter
        mPresenter = createPresenter();
        // Presenter与View建立关联
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 解除Presenter与View的关联
        mPresenter.detachView();
    }

    protected abstract P createPresenter();

    @Override
    public void showShort(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void showLong(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void showTipDialog(String tipMsg, final boolean isFinishWhenCancel) {
        if (tipDialog == null) {
            tipDialog = new Dialog(getContext(), R.style.Dialog_No_Border);
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
                    getFragmentManager().popBackStack();
                }
            }
        });
        TextView tv_tip = (TextView) tipDialog.findViewById(R.id.tv_tip);
        tv_tip.setText(tipMsg);
        try {
            tipDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
