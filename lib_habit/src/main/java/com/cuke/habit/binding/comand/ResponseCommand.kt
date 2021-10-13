package com.cuke.habit.binding.comand

import io.reactivex.functions.Function
import java.lang.Exception

class ResponseCommand<T, R> {

    private var execute: BindingFunction<R>? = null
    private var function: Function<T, R>? = null
    private var canExecute: BindingFunction<Boolean>? = null

    /**
     * like [BindingCommand],but ResponseCommand can return result when command has executed!
     *
     * @param execute function to execute when event occur.
     */
    fun ResponseCommand(execute: BindingFunction<R>?) {
        this.execute = execute
    }


    fun ResponseCommand(execute: Function<T, R>?) {
        function = execute
    }


    fun ResponseCommand(execute: BindingFunction<R>?, canExecute: BindingFunction<Boolean>?) {
        this.execute = execute
        this.canExecute = canExecute
    }


    fun ResponseCommand(execute: Function<T, R>?, canExecute: BindingFunction<Boolean>?) {
        function = execute
        this.canExecute = canExecute
    }


    fun execute(): R? {
        return if (execute != null && canExecute()) {
            execute!!.call()
        } else null
    }

    private fun canExecute(): Boolean {
        return if (canExecute == null) {
            true
        } else canExecute!!.call()
    }


    @Throws(Exception::class)
    fun execute(parameter: T): R? {
        return if (function != null && canExecute()) {
            function!!.apply(parameter)
        } else null
    }
}