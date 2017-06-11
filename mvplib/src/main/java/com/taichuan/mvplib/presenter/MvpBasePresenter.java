package com.taichuan.mvplib.presenter;

import android.os.Handler;
import android.os.Message;

import com.taichuan.mvplib.view.viewimpl.ViewBaseInterface;

import java.lang.ref.WeakReference;

/**
 * Created by gui on 2017/5/28.
 * Presenter基类
 */
public class MvpBasePresenter<V extends ViewBaseInterface> {
    private WeakReference<V> mViewWeak;
    protected MyHandler mHandler;

    protected static class MyHandler extends Handler {
        private WeakReference<MvpBasePresenter> weak;

        public MyHandler(MvpBasePresenter mvpBasePresenter) {
            weak = new WeakReference<>(mvpBasePresenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            weak.get().handleMessage(msg);
        }
    }

    protected void handleMessage(Message msg) {

    }

    public V getView() {
        return mViewWeak == null ? null : mViewWeak.get();
    }

    /**
     * 判断是否有与view建立了联系
     */
    public boolean isViewAttached() {
        return mViewWeak != null && mViewWeak.get() != null;
    }

    /**
     * 与View建立关联
     *
     * @param view MvpBaseView类型接口
     */
    public void attachView(V view) {
        mViewWeak = new WeakReference<>(view);
    }

    /**
     * 解除与View的关联
     */
    public void detachView() {
        if (mViewWeak != null) {
            mViewWeak.clear();
            mViewWeak = null;
        }
    }
}
