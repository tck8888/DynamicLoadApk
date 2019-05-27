package com.tck.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.Nullable;

/**
 * <p>description:代理activity，管理插件activity的生命周期</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public class ProxyActivity2 extends Activity {

    private String className;


    private IPlugin iPlugin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        try {
            Class<?> aClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object o = constructor.newInstance(new Object[]{});
            if (o instanceof IPlugin) {
                iPlugin = (IPlugin) o;
                iPlugin.attach(this);
                Bundle bundle = new Bundle();
                iPlugin.onCreate(bundle);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (iPlugin != null) {
            iPlugin.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (iPlugin != null) {
            iPlugin.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iPlugin != null) {
            iPlugin.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (iPlugin != null) {
            iPlugin.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPlugin != null) {
            iPlugin.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (iPlugin != null) {
            iPlugin.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (iPlugin != null) {
            iPlugin.onSaveInstanceState(outState);
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public AssetManager getAssets() {
        return PluginManager.getInstance().getAssetManager();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {

        String classNameFrom = intent.getStringExtra("className");
        Intent newIntent = new Intent(this, ProxyActivity2.class);
        newIntent.putExtra("className", classNameFrom);
        super.startActivity(newIntent);

    }


}
