package com.cuke.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BasePresenter<in IBaseView>, V : IBaseView> : AppCompatActivity(),
    IBaseView {

    private var presenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this)
    }

    abstract fun createPresenter(): T

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }


    fun showMessage(message: String) {

    }

}