package com.cuke.habit.binding.viewadapter.switch

import android.widget.CompoundButton
import android.widget.Switch
import androidx.databinding.BindingAdapter
import com.cuke.habit.binding.comand.BindingCommand

object ViewAdapter {

    /**
     * 设置开关状态
     *
     * @param mSwitch Switch控件
     */
    @BindingAdapter("switchState")
    fun setSwitchState(mSwitch: Switch, isChecked: Boolean) {
        mSwitch.isChecked = isChecked
    }

    /**
     * Switch的状态改变监听
     *
     * @param mSwitch        Switch控件
     * @param changeListener 事件绑定命令
     */
    @BindingAdapter("onCheckedChangeCommand")
    fun onCheckedChangeCommand(mSwitch: Switch, changeListener: BindingCommand<Boolean?>?) {
        if (changeListener != null) {
            mSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                changeListener.execute(
                    isChecked
                )
            }
        }
    }
}