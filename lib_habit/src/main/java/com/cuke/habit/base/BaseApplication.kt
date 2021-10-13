package com.cuke.habit.base

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.cuke.habit.utils.ContextUtils
import java.lang.NullPointerException

class BaseApplication : Application(){
    private var sInstance: Application? = null

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    @Synchronized
    fun setApplication(application: Application) {
        sInstance = application
        //初始化工具类
        ContextUtils.init(application)
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppManager.instance.addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                AppManager.instance.removeActivity(activity)
            }
        })
    }

    /**
     * 获得当前app运行的Application
     */
    fun getInstance(): Application? {
        if (sInstance == null) {
            throw NullPointerException("please inherit BaseApplication or call setApplication.")
        }
        return sInstance
    }
}