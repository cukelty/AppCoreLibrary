package com.cuke.base.utils

import android.content.Context

class SP(context: Context, var spName: String) {
    private var spManager: SharedPreferenceManager = SharedPreferenceManager.get(context.applicationContext)!!
    private fun mappingKey(key: String): String {
        return spName + "_" + key
    }

    fun getString(key: String, defaultValue: String): String? {
        return spManager.get(mappingKey(key), defaultValue)
    }

    fun putString(key: String, value: String) {
        spManager.put(mappingKey(key), value)
    }

    fun putInt(key: String, value: Int) {
        spManager.put(mappingKey(key), value)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return spManager.get(mappingKey(key), defaultValue)
    }

    fun putLong(key: String, value: Long) {
        spManager.put(mappingKey(key), value)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return spManager.get(mappingKey(key), defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        spManager.put(mappingKey(key), value)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return spManager.get(mappingKey(key), defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        spManager.put(mappingKey(key), value)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return spManager.get(mappingKey(key), defaultValue)
    }

    fun remove(key: String) {
        spManager.remove(mappingKey(key))
    }

    fun clear() {
        spManager.clear()
    }

    val all: Map<String, *>
        get() = spManager.all

    companion object {
        fun getDefaultSP(context: Context): SP {
            return SP(context, "Default_SP")
        }

        fun getSP(context: Context, spName: String): SP {
            return SP(context, spName)
        }
    }

}