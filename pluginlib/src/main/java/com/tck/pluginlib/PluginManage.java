package com.tck.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class PluginManage {


    public static volatile PluginManage instance;

    private PluginAPK pluginAPK;

    private PluginManage() {

    }

    public static PluginManage getInstance() {
        if (instance == null) {
            synchronized (PluginManage.class) {
                if (instance == null) {
                    instance = new PluginManage();
                }
            }
        }
        return instance;
    }

    public Context context;

    public void init(Context context) {
        this.context = context;
    }

    public void loadApk(String apkPath) {
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);

        if (packageInfo == null) {
            return;
        }

        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResources(am);
        pluginAPK = new PluginAPK(packageInfo, resources, classLoader);

    }

    public PluginAPK getPluginAPK() {
        return pluginAPK;
    }

    private Resources createResources(AssetManager am) {
        Resources resources = context.getResources();
        return new Resources(am, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            return assetManager;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = context.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(
                apkPath,
                file.getAbsolutePath(),
                null,
                context.getClassLoader());
    }

}
