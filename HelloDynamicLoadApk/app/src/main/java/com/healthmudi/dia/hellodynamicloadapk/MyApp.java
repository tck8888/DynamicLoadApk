package com.healthmudi.dia.hellodynamicloadapk;

import android.app.Application;
import android.content.Context;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/19 10:57</p>
 *
 * @author tck
 * @version 3.3
 */
public class MyApp extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
