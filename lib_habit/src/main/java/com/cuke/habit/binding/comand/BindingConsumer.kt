package com.cuke.habit.binding.comand

interface BindingConsumer<T> {
    fun call(t: T)
}