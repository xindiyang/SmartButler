package com.example.codoon.smartbutler.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.codoon.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.application
 * 文件名：BaseApplication
 * 创建时间：2018/10/8 上午10:40
 * 描述：TODO
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
    private Context context;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(context);
    }
}
