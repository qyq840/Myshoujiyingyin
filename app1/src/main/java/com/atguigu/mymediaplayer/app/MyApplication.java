package com.atguigu.mymediaplayer.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by  on 2017/1/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
