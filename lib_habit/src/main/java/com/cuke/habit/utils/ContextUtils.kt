package com.cuke.habit.utils

import android.annotation.SuppressLint
import android.content.Context
import java.lang.NullPointerException
import java.lang.UnsupportedOperationException

class ContextUtils private constructor() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context) {
            Companion.context = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getContext(): Context? {
            if (context != null) {
                return context
            }
            throw NullPointerException("should be initialized in application")
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}