package com.cuke.base.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cuke.base.base.mvp.IBaseView
import com.cuke.base.utils.ToastUtils

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    private var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        initBind()
        initViews(savedInstanceState)
        initData()
    }

    protected abstract fun getLayoutId() :Int
    protected abstract fun initBind()
    protected abstract fun initViews(savedInstanceState: Bundle?)
    protected abstract fun initData()


    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    open fun gotoActivity(clz: Class<*>?) {
        gotoActivity(clz, false, null)
    }

    /**
     * @gotoActivity
     * open activity and close current;
     */
    open fun gotoActivity(clz: Class<*>?, isCloseCurrentActivity: Boolean) {
        gotoActivity(clz, isCloseCurrentActivity, null)
    }

    open fun gotoActivity(clz: Class<*>?, isCloseCurrentActivity: Boolean, ex: Bundle?) {
        val intent = Intent(this, clz)
        if (ex != null) intent.putExtras(ex)
        startActivity(intent)
        if (isCloseCurrentActivity) {
            finish()
        }
    }


    fun showMessage(message: String) {
        ToastUtils.init(true)
        ToastUtils.showLongToast(this,message)
    }

}