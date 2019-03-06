package com.singlethread.rcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.Map;

/**
 * Created by litianyuan on 2017/8/31.
 */

public class SPUtils {
    /**
     * 不可实例化
     */
    private SPUtils(){
        throw new UnsupportedOperationException("cannot be instanted");
    }
    /**
     * SP存储文件名
     */
    public static final String FILE_NAME = "rcore_sp";

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 根据数据类型保存数据
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences.Editor editor = getSP(context).edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    /**
     * 根据默认值类型获取数据
     */
    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sp = getSP(context);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }


    /**
     * 保存数据，已有默认数据
     */
    public static boolean getBoolean(Context context, String key) {
        return getSP(context).getBoolean(key, false);
    }

    public static String getString(Context context, String key) {
        return getSP(context).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return getSP(context).getInt(key, 0);
    }


    /**
     * 移除key对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(getSP(context).edit().remove(key));
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(getSP(context).edit().clear());
    }

    /**
     * 查询key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        return getSP(context).contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        return getSP(context).getAll();
    }

}
