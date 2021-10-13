package com.cuke.habit.base

class MultiItemViewModel<VM : BaseViewModel<in BaseModel>>(viewModel: VM) : ItemViewModel<VM>(viewModel) {
    var itemType: Any? = null
        protected set

    fun multiItemType(multiType: Any) {
        itemType = multiType
    }
}