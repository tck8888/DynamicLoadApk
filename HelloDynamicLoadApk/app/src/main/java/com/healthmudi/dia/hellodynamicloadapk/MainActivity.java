package com.healthmudi.dia.hellodynamicloadapk;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录
    private DexClassLoader classLoader = null;

    private String apkName = "app-debug.apk";    //apk名称

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File extractFile = this.getFileStreamPath(apkName);
        dexpath = extractFile.getPath();
        fileRelease = getDir("dex", 0); //0 表示Context.MODE_PRIVATE

        Log.d(TAG, "dexpath:" + dexpath);
        Log.d(TAG, "fileRelease.getAbsolutePath():" +
                fileRelease.getAbsolutePath());

        classLoader = new DexClassLoader(dexpath,
                fileRelease.getAbsolutePath(), null, getClassLoader());

        Button btn_1 = (Button) findViewById(R.id.btn_1);

        tv = (TextView) findViewById(R.id.tv);

        //普通调用，反射的方式
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Class mLoadClassBean;
                try {
                    mLoadClassBean = classLoader.loadClass("com.healthmudi.dia.loadapk.UserInfoBean");
                    Object beanObject = mLoadClassBean.newInstance();

                    Method getNameMethod = mLoadClassBean.getMethod("getNickname");
                    getNameMethod.setAccessible(true);
                    String name = (String) getNameMethod.invoke(beanObject);

                    tv.setText(name);
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.e(TAG, "msg:" + e.getMessage());
                }
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, apkName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
