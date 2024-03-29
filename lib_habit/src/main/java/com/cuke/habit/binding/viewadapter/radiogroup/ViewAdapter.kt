package com.cuke.habit.binding.viewadapter.radiogroup

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.cuke.habit.binding.comand.BindingCommand

object ViewAdapter {
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun onCheckedChangedCommand(radioGroup: RadioGroup, bindingCommand: BindingCommand<String?>) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton =
                group.findViewById<View>(checkedId) as RadioButton
            bindingCommand.execute(radioButton.text.toString())
        }
    }
}