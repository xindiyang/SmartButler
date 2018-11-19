package com.example.codoon.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.utils
 * 文件名：ShareUtil
 * 创建时间：2018/11/6 下午3:46
 * 描述：SharePreference封装
 */
public class ShareUtil {
    public static final String NAME = "config";

    /**
     * 字符串型
     * 存数据、
     * 键 值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 字符串型
     * 读数据
     * 键 默认值
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 整型
     * 存数据、
     * 键 值
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 整型
     * 读数据
     * 键 默认值
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 布尔型
     * 存数据、
     * 键 值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 布尔型
     * 读数据
     * 键 默认值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 删除 单个
     */
    public static void deleShare(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    /**
     * 删除 全部
     */
    public static void deleAll(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
