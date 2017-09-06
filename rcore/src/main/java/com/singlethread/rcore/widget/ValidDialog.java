package com.singlethread.rcore.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

/**
 * Created with Android Studio
 * PackageName: com.singlethread.rcore.widget
 * Created by litianyuan on 2017/9/6.
 */

public abstract class ValidDialog extends Dialog{

    private Activity mContext;

    public ValidDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContext(context);
    }

    public ValidDialog(Context context, int theme) {
        super(context, theme);
        setContext(context);
    }

    public ValidDialog(Context context) {
        super(context);
        setContext(context);
    }

    private void setContext(Context context) {
        if (context instanceof Activity) {
            mContext = (Activity) context;
        }
    }

    @Override
    public void show() {
        if (isActivityActive()) {
            try {
                super.show();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void dismiss() {

        if (isActivityActive()) {
            try {
                super.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isActivityActive() {
        return mContext == null || !mContext.isFinishing();
    }

}
