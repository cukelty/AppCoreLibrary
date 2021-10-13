package com.cuke.habit.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.cuke.habit.bus.event.SingleLiveEvent

class UIChangeLiveData : SingleLiveEvent<Any>() {

    val showDialogEvent: SingleLiveEvent<String> = createLiveData(null)
    val dismissDialogEvent: SingleLiveEvent<Void> = createLiveData(null)
    val startActivityEvent: SingleLiveEvent<Map<String, Any?>> = createLiveData(null)
    val startContainerActivityEvent: SingleLiveEvent<Map<String, Any?>> = createLiveData(null)
    val finishEvent: SingleLiveEvent<Void> = createLiveData(null)
    val onBackPressedEvent: SingleLiveEvent<Void> = createLiveData(null)

    private fun <T> createLiveData(liveData: SingleLiveEvent<T>?): SingleLiveEvent<T> {
        var liveData: SingleLiveEvent<T>? = liveData
        if (liveData == null) {
            liveData = SingleLiveEvent()
        }
        return liveData
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<Any>) {
        super.observe(owner, observer)
    }
}