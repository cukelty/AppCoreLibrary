package com.cuke.habit.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.LifecycleProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.HashMap

class BaseViewModel<M : BaseModel> : AndroidViewModel, IBaseViewModel {
    protected var model: M? = null

    private var uc: UIChangeLiveData? = null

    //弱引用持有
    private var lifecycle: WeakReference<LifecycleProvider<Any>>? = null

    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable? = null

    constructor(application: Application, model: M?):super(application){
        this.model = model
        mCompositeDisposable = CompositeDisposable()
    }

    constructor(application: Application):this(application,null)

    protected fun addSubscribe(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<Any>) {
        this.lifecycle = WeakReference<LifecycleProvider<Any>>(lifecycle)
    }

    fun getLifecycleProvider(): LifecycleProvider<Any>? {
        return lifecycle!!.get()
    }

    fun showDialog() {
        showDialog("请稍后...")
    }

    fun showDialog(title: String?) {
        uc!!.showDialogEvent.postValue(title)
    }

    fun dismissDialog() {
        uc!!.dismissDialogEvent.call()
    }

    fun getUC(): UIChangeLiveData? {
        if (uc == null) {
            uc = UIChangeLiveData()
        }
        return uc
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>?) {
        startActivity(clz, null)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val params: MutableMap<String, Any?> = HashMap()
        params[ParameterField.CLASS] = clz
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uc!!.startActivityEvent.postValue(params)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String?) {
        startContainerActivity(canonicalName, null)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String?, bundle: Bundle?) {
        val params: MutableMap<String, Any?> = HashMap()
        params[ParameterField.CANONICAL_NAME] = canonicalName
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uc!!.startContainerActivityEvent.postValue(params)
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uc!!.finishEvent.call()
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        uc!!.onBackPressedEvent.call()
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun onStart() {}

    override fun onStop() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun registerRxBus() {}

    override fun removeRxBus() {}

    override fun onCleared() {
        super.onCleared()
        if (model != null) {
            model!!.onCleared()
        }
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    @Throws(Exception::class)
    fun accept(disposable: Disposable?) {
        addSubscribe(disposable)
    }
}