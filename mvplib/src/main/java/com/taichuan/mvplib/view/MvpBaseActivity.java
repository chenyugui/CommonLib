package com.taichuan.mvplib.view;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taichuan.mvplib.R;
import com.taichuan.mvplib.presenter.MvpBasePresenter;
import com.taichuan.mvplib.view.support.MySupportActivity;
import com.taichuan.mvplib.view.viewimpl.ViewBaseInterface;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by gui on 2017/5/27.
 * activity View层基类
 */
public abstract class MvpBaseActivity<V extends ViewBaseInterface, P extends MvpBasePresenter<V>>
        extends MySupportActivity
        implements ViewBaseInterface {
    @SuppressWarnings("unused")
    protected final String TAG = getClass().getSimpleName().replace("Activity", "Act");
    protected MvpBaseActivity instance;
    protected P mPresenter;
    private Dialog tipDialog;
    private Toast mToast;

    /**
     * 订阅切断者容器
     */
    private CompositeDisposable compositeDisposable;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        // 切断所有订阅
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    protected abstract P createPresenter();

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @SuppressWarnings({"unchecked", "unused"})
    protected <T extends View> T findView(int viewID) {
        return (T) findViewById(viewID);
    }

    @SuppressWarnings({"unchecked", "unused"})
    protected <T extends View> T findView(View view, int viewID) {
        return (T) view.findViewById(viewID);
    }

    @Override
    public void showShort(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }


    @Override
    public void showLong(String text) {
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
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

    @SuppressWarnings("unused")
    public void toActivity(Class activityClass, boolean isFinish) {
        Intent it = new Intent();
        it.setComponent(new ComponentName(this, activityClass));
        startActivity(it);
        if (isFinish)
            finish();
    }
}
