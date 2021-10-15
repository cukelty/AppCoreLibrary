package com.cuke.habit.binding.viewadapter.view

import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.databinding.BindingAdapter
import com.cuke.habit.binding.comand.BindingCommand
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

object ViewAdapter {

    //防重复点击间隔(秒)
    const val CLICK_INTERVAL = 1

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
    fun onClickCommand(view: View, clickCommand: BindingCommand<View>?, isThrottleFirst: Boolean) {
        if (isThrottleFirst) {
            view.setOnClickListener {
                clickCommand?.execute()
            }
        } else {
            view.setOnClickListener{
                clickCommand?.execute()
            }
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = ["onLongClickCommand"], requireAll = false)
    fun onLongClickCommand(view: View, clickCommand: BindingCommand<View>?) {
        view.setOnLongClickListener {
            clickCommand?.execute()
            true
        }
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = ["currentView"], requireAll = false)
    fun replyCurrentView(currentView: View?, bindingCommand: BindingCommand<View?>?) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView)
        }
    }

    /**
     * view是否需要获取焦点
     */
    @BindingAdapter("requestFocus")
    fun requestFocusCommand(view: View, needRequestFocus: Boolean) {
        if (needRequestFocus) {
            view.isFocusableInTouchMode = true
            view.requestFocus()
        } else {
            view.clearFocus()
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter("onFocusChangeCommand")
    fun onFocusChangeCommand(view: View, onFocusChangeCommand: BindingCommand<Boolean?>?) {
        view.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            onFocusChangeCommand?.execute(hasFocus)
        }
    }

    /**
     * view的显示隐藏
     */
    @BindingAdapter(value = ["isVisible"], requireAll = false)
    fun isVisible(view: View, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}