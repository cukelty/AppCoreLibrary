package com.singlethread.rcore.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.singlethread.rcore.R;

/**
 * Created with Android Studio
 * PackageName: com.singlethread.rcore.widget
 * Created by litianyuan on 2017/9/6.
 */

public class LoadingDialog extends ValidDialog {
    protected static final String TAG = "IphoneDialog";

    private View mView;
    private View loadingIcon;
    private Animation roteAnimation;

    public LoadingDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_bar, null);
        loadingIcon = mView.findViewById(R.id.progressbar);
        setContentView(mView);
        setCanceledOnTouchOutside(false);
        roteAnimation = new RotateAnimation(0.0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        roteAnimation.setRepeatCount(-1);
        roteAnimation.setInterpolator(new LinearInterpolator());
        roteAnimation.setDuration(2000);
    }

    @Override
    public void show() {
        super.show();
        loadingIcon.startAnimation(roteAnimation);
    }

}
