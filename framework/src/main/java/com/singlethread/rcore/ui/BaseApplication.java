package com.singlethread.rcore.ui;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by litianyuan on 2017/8/30.
 */

public abstract class BaseApplication extends Application {

    protected abstract void attachBaseContextOut();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        attachBaseContextOut();

    }
}
