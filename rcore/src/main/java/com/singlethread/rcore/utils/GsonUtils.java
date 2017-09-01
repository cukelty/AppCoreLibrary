package com.singlethread.rcore.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by litianyuan on 2017/8/31.
 */

public class GsonUtils {

    private static Gson sGson = null;

    static {
        if (sGson == null) {
            sGson = new Gson();
        }
    }

    private GsonUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonToString(Object object) {
        String gsonString = null;
        if (sGson != null) {
            gsonString = sGson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToEntity(String gsonString, Class<T> cls) {
        T t = null;
        if (sGson != null) {
            t = sGson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (sGson != null) {
            list = sGson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

//    public <T> ArrayList<T> jsonToList(String json, Class<T> cls) {
//        ArrayList<T> mList = new ArrayList<T>();
//        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
//        for(final JsonElement elem : array){
//            mList.add(mGson.fromJson(elem, cls));
//        }
//        return mList;
//    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (sGson != null) {
            list = sGson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (sGson != null) {
            map = sGson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
