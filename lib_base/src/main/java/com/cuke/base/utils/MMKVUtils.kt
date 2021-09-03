package com.cuke.base.utils

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 *  MMKV Utils for multi process
 */
class MMKVUtils {
    private val kv: MMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)

    companion object {
        /**
         * init instance with single
         */
        val instance: MMKVUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MMKVUtils()
        }
    }

    /**
     * save obj with key
     * support String,Int,Boolean,Float,Long,Double,ByteArray
     * other will trans string
     */
    fun encode(key: String?, obj: Any) {
        when (obj) {
            is String -> kv.encode(key, obj)
            is Int -> kv.encode(key, obj)
            is Boolean -> kv.encode(key, obj)
            is Float -> kv.encode(key, obj)
            is Long -> kv.encode(key, obj)
            is Double -> kv.encode(key, obj)
            is ByteArray -> kv.encode(key, obj)
            is Parcelable -> kv.encode(key, obj)
            is Any -> kv.encode(key, obj.toString())
        }
    }

    /**
     * save set
     */
    fun encodeSet(key: String?, set: Set<String>) {
        kv.encode(key, set)
    }

    fun decodeBool(key: String?, defaultValue: Boolean): Boolean {
        return kv.decodeBool(key, defaultValue)
    }

    fun decodeBytes(key: String?, defaultValue: ByteArray): ByteArray? {
        return kv.decodeBytes(key, defaultValue)
    }

    fun decodeDouble(key: String?, defaultValue: Double): Double {
        return kv.decodeDouble(key, defaultValue)
    }

    fun decodeString(key: String?, defaultValue: String): String? {
        return kv.decodeString(key, defaultValue)
    }

    fun decodeString(key: String?): String? {
        return kv.decodeString(key, "")
    }

    fun decodeLong(key: String?, defaultValue: Long): Long {
        return kv.decodeLong(key, defaultValue)
    }

    fun decodeFloat(key: String?, defaultValue: Float): Float {
        return kv.decodeFloat(key, defaultValue)
    }

    fun decodeInt(key: String?, defaultValue: Int): Int {
        return kv.decodeInt(key, defaultValue)
    }

    fun <T : Parcelable?> decodeParcelable(key: String?, cls: Class<T>, defaultValue: T): T? {
        return kv.decodeParcelable(key, cls, defaultValue)
    }

    fun decodeSet(
        key: String?,
        defaultValue: Set<String>,
        cls: Class<out Set<String>>
    ): MutableSet<String>? {
        return kv.decodeStringSet(key, defaultValue, cls)
    }

    /**
     * init in application
     * first to call
     */
//    fun initialize(context: Context?) {
//        MMKV.initialize(context)
//    }

    /**
     * remove value
     */
    fun removeValue(key: String?) {
        kv.removeValueForKey(key)
    }

    /**
     * clear all
     */
    fun clear() {
        kv.clearAll()
    }

    fun decodeLong(key: String): Long {
        return decodeLong(key, 0L)
    }
}

object InitMMKV {
    fun init(context: Context) {
        MMKV.initialize(context)
    }
}