package com.tck.dynamicloadapk;

import android.app.Application;

import com.tck.pluginlib.PluginManage;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/26</p>
 *
 * @author tck
 * @version 1.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PluginManage.getInstance().init(this);
    }
}
