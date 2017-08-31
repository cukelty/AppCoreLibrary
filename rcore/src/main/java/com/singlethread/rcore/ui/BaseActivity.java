package com.singlethread.rcore.ui;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by litianyuan on 2017/8/31.
 */

public abstract class BaseActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        EventBus.getDefault().register(this);
        setContentView();
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView( );

    protected abstract void initEvent();

    protected void setContentView() {
        setContentView(getContentViewId());
    }

    protected abstract int getContentViewId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
