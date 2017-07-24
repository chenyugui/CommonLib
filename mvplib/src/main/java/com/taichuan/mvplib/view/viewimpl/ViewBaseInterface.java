package com.taichuan.mvplib.view.viewimpl;

import io.reactivex.disposables.Disposable;

/**
 * Created by gui on 2017/5/28.
 * view接口基类
 */
public interface ViewBaseInterface {
    void showShort(String text);

    void showLong(String text);

    /**
     * 显示提示对话框
     *
     * @param tipMsg             要提示的内容
     * @param isFinishWhenCancel 当对话框消失时，Activity是否进行finish（如果view是Activity）/ Fragment是否回退（如果view是Fragment，并且有addToBackStack）
     */
    void showTipDialog(String tipMsg, final boolean isFinishWhenCancel);

    void addDisposable(Disposable disposable);
}
