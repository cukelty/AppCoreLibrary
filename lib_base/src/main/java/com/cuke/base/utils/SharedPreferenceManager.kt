package com.cuke.base.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Process
import android.preference.PreferenceManager
import android.util.Pair
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*

class SharedPreferenceManager private constructor(context: Context) : Observer {
    private var mPreference: SharedPreferences? = null
    private var mContext: Context? = null
    private val mUpdateQueue = LinkedList<Pair<String, Any?>>()
    private val cacheMap = HashMap<String, Any>()
    lateinit var mCurrentUpdateThread: Thread

    /**
     * Release all resources
     */
    fun close() {
        mPreference = null
        mInstance = null
    }

    private val isPreferenceNull: Boolean
        private get() = if (mPreference == null) true else false

    override fun update(observable: Observable, data: Any) {
        if (data is Pair<*, *>) {
            synchronized(mUpdateQueue) {
                if (data != null) {
                    val pair =
                        data as Pair<String, Any?>
                    mUpdateQueue.add(pair)
                }
            }
            writePreferenceSlowly()
        }
    }

    /*
     * Do Hibernation slowly
     */
    private fun writePreferenceSlowly() {
        if (mCurrentUpdateThread != null) {
            if (mCurrentUpdateThread!!.isAlive) {
                // the update thread is still running,
                // so no need to start a new one
                return
            }
        }
        mCurrentUpdateThread = object : Thread() {
            override fun run() {
                try {
                    // sleep 10secs to wait some concurrent task be
                    // inserted into the task queue
                    sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                writePreference()
            }
        }
        mCurrentUpdateThread.setPriority(Process.THREAD_PRIORITY_BACKGROUND)
        mCurrentUpdateThread.start()
    }

    /*
     * Do Hibernation immediately
     */
    fun writePreferenceQuickly() {
        mCurrentUpdateThread = object : Thread() {
            override fun run() {
                writePreference()
            }
        }
        mCurrentUpdateThread.setPriority(Process.THREAD_PRIORITY_BACKGROUND)
        mCurrentUpdateThread.start()
    }

    /**
     * Write session value back to preference
     */
    private fun writePreference() {
        val editor = mPreference!!.edit()
        synchronized(mUpdateQueue) {
            while (!mUpdateQueue.isEmpty()) {
                try {
                    val updateItem = mUpdateQueue.remove()
                    val key = updateItem.first
                    val value = updateItem.second
                    // System.out.println(key+":"+value);
                    if (value == null) {
                        editor.putString(key, null)
                    } else if (value is String) {
                        editor.putString(key, value as String?)
                    } else if (value is Int) {
                        editor.putInt(key, (value as Int?)!!)
                    } else if (value is Long) {
                        editor.putLong(key, (value as Long?)!!)
                    } else if (value is Float) {
                        editor.putFloat(key, (value as Float?)!!)
                    } else if (value is Boolean) {
                        editor.putBoolean(key, (value as Boolean?)!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        apply(editor)
    }

    operator fun get(key: String, defaultValue: String): String? {
        val obj = cacheMap[key]
        return if (obj != null && obj is String) {
            obj.toString()
        } else {
            mPreference!!.getString(key, defaultValue)
        }
    }

    fun put(key: String, value: String) {
        // System.out.println("put string...");
        cacheMap[key] = value
        mUpdateQueue.add(Pair(key, value))
        writePreferenceSlowly()
    }

    fun put(key: String, value: Int) {
        cacheMap[key] = value
        mUpdateQueue.add(Pair(key, value))
        writePreferenceSlowly()
    }

    operator fun get(key: String, defaultValue: Int): Int {
        val obj = cacheMap[key]
        return if (obj != null && obj is Int) {
            obj.toInt()
        } else {
            mPreference!!.getInt(key, defaultValue)
        }
    }

    fun put(key: String, value: Float) {
        cacheMap[key] = value
        mUpdateQueue.add(Pair(key, value))
        writePreferenceSlowly()
    }

    operator fun get(key: String, defaultValue: Float): Float {
        val `object` = cacheMap[key]
        return if (`object` != null && `object` is Float) {
            `object`.toFloat()
        } else {
            mPreference!!.getFloat(key, defaultValue)
        }
    }

    fun put(key: String, value: Long) {
        cacheMap[key] = value
        mUpdateQueue.add(Pair(key, value))
        writePreferenceSlowly()
    }

    operator fun get(key: String, defaultValue: Long): Long {
        val `object` = cacheMap[key]
        return if (`object` != null && `object` is Double) {
            (`object` as Long).toLong()
        } else {
            mPreference!!.getLong(key, defaultValue)
        }
    }

    fun put(key: String, value: Boolean) {
        cacheMap[key] = value
        mUpdateQueue.add(Pair(key, value))
        writePreferenceSlowly()
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        val `object` = cacheMap[key]
        return if (`object` != null && `object` is Boolean) {
            `object`
        } else {
            mPreference!!.getBoolean(key, defaultValue)
        }
    }

    fun remove(key: String) {
        val value = cacheMap.remove(key)
        mUpdateQueue.remove(Pair(key, value))
        mPreference!!.edit().remove(key).commit()
    }

    fun clear() {
        cacheMap.clear()
        mPreference!!.edit().clear().commit()
    }

    val all: Map<String, *>
        get() = mPreference!!.all

    companion object {
        private var mInstance: SharedPreferenceManager? = null
        operator fun get(context: Context): SharedPreferenceManager? {
            if (mInstance == null) {
                mInstance = SharedPreferenceManager(context)
            }
            return mInstance
        }

        private val sApplyMethod = findApplyMethod()
        private fun findApplyMethod(): Method? {
            try {
                val cls = SharedPreferences.Editor::class.java
                return cls.getMethod("apply")
            } catch (unused: NoSuchMethodException) {
                // fall through
            }
            return null
        }

        /**
         * Use this method to modify preference
         */
        fun apply(editor: SharedPreferences.Editor) {
            if (sApplyMethod != null) {
                try {
                    sApplyMethod.invoke(editor)
                    return
                } catch (unused: InvocationTargetException) {
                    // fall through
                } catch (unused: IllegalAccessException) {
                    // fall through
                }
            }
            editor.commit()
        }
    }

    init {
        synchronized(this) {
            mContext = context.applicationContext
            if (mPreference == null) {
                mPreference = PreferenceManager.getDefaultSharedPreferences(mContext)
            }
        }
    }
}