package com.cuke.habit.binding.comand

class BindingCommand<T> {

    private var execute: BindingAction? = null
    private var consumer: BindingConsumer<T>? = null
    private var canExecute0: BindingFunction<Boolean>? = null

    fun BindingCommand(execute: BindingAction?) {
        this.execute = execute
    }

    /**
     * @param execute 带泛型参数的命令绑定
     */
    fun BindingCommand(execute: BindingConsumer<T>) {
        consumer = execute
    }

    /**
     * @param execute     触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    fun BindingCommand(execute: BindingAction?, canExecute0: BindingFunction<Boolean>) {
        this.execute = execute
        this.canExecute0 = canExecute0
    }

    /**
     * @param execute     带泛型参数触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    fun BindingCommand(execute: BindingConsumer<T>, canExecute0: BindingFunction<Boolean>) {
        consumer = execute
        this.canExecute0 = canExecute0
    }

    /**
     * 执行BindingAction命令
     */
    fun execute() {
        if (execute != null && canExecute0()) {
            execute!!.call()
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    fun execute(parameter: T) {
        if (consumer != null && canExecute0()) {
            consumer!!.call(parameter)
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private fun canExecute0(): Boolean {
        return canExecute0?.call() ?: true
    }
}