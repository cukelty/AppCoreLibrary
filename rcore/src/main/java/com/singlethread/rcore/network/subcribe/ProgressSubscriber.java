package com.singlethread.rcore.network.subcribe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.singlethread.rcore.R;
import com.singlethread.rcore.network.exception.ApiException;
import com.singlethread.rcore.ui.IContext;
import com.singlethread.rcore.utils.NetworkUtil;
import com.singlethread.rcore.utils.ToastMaster;
import com.singlethread.rcore.widget.LoadingDialog;

import rx.Subscriber;

/**
 * Created with Android Studio
 * PackageName: com.singlethread.rcore.network.subcribe
 * Created by litianyuan on 2017/9/6.
 */

public abstract class ProgressSubscriber<T>  extends Subscriber<T> {
    private Dialog mDialog;

    private IContext context;

    public ProgressSubscriber(IContext context) {
        this(context, new LoadingDialog(context.getContext()));
    }

    public ProgressSubscriber(IContext context, Dialog dialog) {
        this.context = context;
        mDialog = dialog;
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (!isUnsubscribed()) {
                    unsubscribe();
                }
            }
        });
    }

    private void showProgressDialog() {
        mDialog.show();
    }

    private void dismissProgressDialog() {
        mDialog.dismiss();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        // Toast.makeText(context.getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            Toast.makeText(context.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkUtil.isNetworkAvailable(context.getContext())) {
                ToastMaster.shortToast(context.getContext(), R.string.request_failed);
            } else {
                ToastMaster.shortToast(context.getContext(),R.string.network_unavailable);
            }
        }
        dismissProgressDialog();

    }




}
