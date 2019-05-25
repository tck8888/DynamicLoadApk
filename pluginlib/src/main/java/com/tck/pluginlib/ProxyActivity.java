package com.tck.pluginlib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * <p>description:代理activity，管理插件activity的生命周期</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class ProxyActivity extends Activity {

    private String className;

    private PluginAPK pluginAPK;

    private IPlugin iPlugin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");

        pluginAPK = PluginManage.getInstance().getPluginAPK();

        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (pluginAPK == null) {
            throw new RuntimeException("请初始化 pluginAPK");
        }
        try {
            Class<?> aClass = pluginAPK.dexClassLoader.loadClass(className);
            Object o = aClass.newInstance();
            if (o instanceof IPlugin) {
                IPlugin o1 = (IPlugin) o;
                o1.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERANL);
                o1.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Resources getResources() {
        if (pluginAPK != null) {
            return pluginAPK.resources;
        }
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        if (pluginAPK != null) {
            return pluginAPK.assetManager;
        }
        return super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (pluginAPK != null) {
            return pluginAPK.dexClassLoader;
        }
        return super.getClassLoader();
    }
}
