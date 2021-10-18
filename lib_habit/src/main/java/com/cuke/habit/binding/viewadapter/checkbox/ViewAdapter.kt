package com.cuke.habit.binding.viewadapter.checkbox

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.cuke.habit.binding.comand.BindingCommand

object ViewAdapter {
    /**
     * @param bindingCommand //绑定监听
     */
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun setCheckedChanged(checkBox: CheckBox, bindingCommand: BindingCommand<Boolean?>) {
        checkBox.setOnCheckedChangeListener { _, b -> bindingCommand.execute(b) }
    }
}
