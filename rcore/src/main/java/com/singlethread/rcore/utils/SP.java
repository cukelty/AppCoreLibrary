package com.singlethread.rcore.utils;

import android.content.Context;

import com.singlethread.rcore.SharedPreferenceManager;

import java.util.Map;

/**
 * Created by liurenhui on 15/3/27. SharedPreference 封装
 */
public class SP {

    public String spName;
    private SharedPreferenceManager spManager;

    /**
     * @param name 作为key的前缀
     */
    public SP(Context context, String name) {
        this.spName = name;
        spManager = SharedPreferenceManager.get(context.getApplicationContext());
    }

    public static SP getDefaultSP(Context context) {
        return new SP(context,"Default_SP");
    }

    public static SP getSP(Context context,String spName) {
        return new SP(context,spName);
    }

    private String mappingKey(String key) {
        return spName + "_" + key;
    }

    public String getString(String key, String defaultValue) {
        return spManager.get(mappingKey(key), defaultValue);
    }

    public void putString(String key, String value) {
        spManager.put(mappingKey(key), value);
    }

    public void putInt(String key, int value) {
        spManager.put(mappingKey(key), value);
    }

    public int getInt(String key, int defaultValue) {
        return spManager.get(mappingKey(key), defaultValue);
    }

    public void putLong(String key, long value) {
        spManager.put(mappingKey(key), value);
    }

    public long getLong(String key, long defaultValue) {
        return spManager.get(mappingKey(key), defaultValue);
    }

    public void putFloat(String key, float value) {
        spManager.put(mappingKey(key), value);
    }

    public float getFloat(String key, float defaultValue) {
        return spManager.get(mappingKey(key), defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        spManager.put(mappingKey(key), value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return spManager.get(mappingKey(key), defaultValue);
    }

    public void remove(String key) {
        spManager.remove(mappingKey(key));
    }

    public void clear() {
        spManager.clear();
    }

    public Map<String, ?> getAll() {
        return spManager.getAll();
    }
}
