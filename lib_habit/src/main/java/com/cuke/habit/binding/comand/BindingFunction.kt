package com.cuke.habit.binding.comand

/**
 * Represents a function with zero arguments.
 *
 * @param <T> the result type
 */
interface BindingFunction<T> {
    fun call(): T
}