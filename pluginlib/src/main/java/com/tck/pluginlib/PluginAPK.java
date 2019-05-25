package com.tck.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class PluginAPK {


    public PackageInfo packageInfo;

    public Resources resources;

    public AssetManager assetManager;

    public DexClassLoader dexClassLoader;

    public PluginAPK(PackageInfo packageInfo, Resources resources, DexClassLoader dexClassLoader) {
        this.packageInfo = packageInfo;
        this.resources = resources;
        this.dexClassLoader = dexClassLoader;
        this.assetManager = resources.getAssets();
    }
}
