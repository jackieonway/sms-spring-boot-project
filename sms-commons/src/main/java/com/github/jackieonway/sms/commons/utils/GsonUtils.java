package com.github.jackieonway.sms.commons.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Google gson util
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class GsonUtils {
    private static Gson gson;

    static {
        gson = new Gson();
    }


    private GsonUtils() {
    }

    /**
     * 对象转成json
     * @param object 待转对象
     * @return json字符串
     */
    public static String gson2String(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * Json转成对象
     * @param gsonString json字符串
     * @param cls 目标对象class
     * @param <T> 目标对象
     * @return 目标对象
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * json转成list
     * Json转成对象
     * @param gsonString json字符串
     * @param cls 目标对象class
     * @param <T> 目标对象
     * @return 目标对象集合
     */
    public static <T> List<T> jsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }


    /**
     * json转成map的
     * Json转成对象
     * @param gsonString json字符串
     * @param <T> 目标对象
     * @return 目标对象Map集合
     */
    public static <T> Map<String, T> jsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 将object对象转成json字符串
     * @param object 待转对象
     * @return json字符串
     */
    public static String json2String(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     * @param gsonString json字符串
     * @param cls 目标对象class
     * @param <T> 目标对象
     * @return 目标对象
     */
    public static <T> T json2Bean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     * @param json json字符串
     * @param cls 目标对象class
     * @param <T> 目标对象
     * @return 目标对象集合
     */
    public static <T> List<T> json2List(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = JsonParser.parseString(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     * @param gsonString json字符串
     * @param <T> 目标对象
     * @return 目标对象
     */
    public static <T> List<Map<String, T>> json2ListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     * @param gsonString json字符串
     * @param <T> 目标对象
     * @return 目标对象Map集合
     */
    public static <T> Map<String, T> json2Maps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 转成map
     * @param gsonString json字符串
     * @param clazz 目标对象
     * @param <T> 目标对象
     * @return 目标对象Map集合
     */
    public static <T> Map<String, T> json2Maps(String gsonString, Class<T> clazz) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
