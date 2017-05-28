package com.taichuan.mvplib.presenter;

import com.taichuan.mvplib.view.viewimpl.ViewBaseInterface;

import java.lang.ref.WeakReference;

/**
 * Created by gui on 2017/5/28.
 * Presenter基类
 */
public class MvpBasePresenter<V extends ViewBaseInterface> {
    protected WeakReference<V> mViewWeak;

    public MvpBasePresenter() {

    }

    public V getView() {
        return mViewWeak == null ? null : mViewWeak.get();
    }

    /**
     * 判断是否有与view建立了联系
     *
     * @return
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
