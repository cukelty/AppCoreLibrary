package com.cuke.base.base.mvp

import android.os.Bundle
import androidx.annotation.Nullable
import com.cuke.base.base.BaseActivity


abstract class BaseMVPActivity<V : IBaseView, P : BasePresenter<V>> : BaseActivity() {

    private lateinit var presenter: P


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    abstract fun initPresenter(): P
}