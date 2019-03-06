package com.singlethread.rcore.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.singlethread.rcore.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by litianyuan on 2017/8/31.
 */

public abstract class BaseActivity extends AppCompatActivity implements IContext{


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


    @Override
    public void finish() {
        super.finish();
        closeAnim();
    }

    protected void closeAnim() {
      ViewUtils.anima(ViewUtils.RIGHT_IN, this);
    }

}
