package com.cuke.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T :BasePresenter<IBaseView>,V: IBaseView> : AppCompatActivity(),IBaseView{

    protected var presenter:T?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter=createPresenter()
        presenter?.attachView(this)
    }

    abstract fun createPresenter():T

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }


    fun showMessage(message:String){

    }

}