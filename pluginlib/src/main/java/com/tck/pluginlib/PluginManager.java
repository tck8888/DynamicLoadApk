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
 * <p>created on: 2019/5/27</p>
 *
 * @author tck
 * @version 1.0
 */
public class PluginManager {

    private static volatile PluginManager instance;

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager();
                }
            }
        }
        return instance;
    }

    private Context context;

    private DexClassLoader dexClassLoader;
    private Resources resources;
    private PackageInfo packageInfo;
    private AssetManager assetManager;

    public void init(Context context) {
        this.context = context;
    }

    public void load(String apkPath) {
        packageInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            return;
        }

        File dex = context.getDir("dex", Context.MODE_PRIVATE);

        dexClassLoader = new DexClassLoader(
                apkPath,
                dex.getAbsolutePath(),
                null,
                context.getClassLoader());

        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (assetManager != null) {
            resources = new Resources(
                    assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        }
    }


    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
