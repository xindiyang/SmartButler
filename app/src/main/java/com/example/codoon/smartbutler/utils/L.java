package com.example.codoon.smartbutler.utils;

import android.util.Log;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.utils
 * 文件名：L
 * 创建时间：2018/11/6 下午3:28
 * 描述：封装log
 */
public class L {
    //开关 true 允许打印log false 禁止打印log
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "SmartButler";
    /**
     * 四个等级 Debug Info Warn Error
     */
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }

}
