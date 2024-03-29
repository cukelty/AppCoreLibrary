package com.cuke.base.base.mvp

import androidx.lifecycle.LifecycleObserver
import java.lang.ref.WeakReference

open class BasePresenter<T : IBaseView> : LifecycleObserver {

    private var baseView: WeakReference<T>? = null

    fun attachView(view: T) {
        baseView = WeakReference(view)
    }

    fun detachView() {
        if (baseView != null) {
            baseView!!.clear()
            baseView = null
        }
    }
}