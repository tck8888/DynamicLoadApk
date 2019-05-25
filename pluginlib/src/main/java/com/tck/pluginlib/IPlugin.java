package com.tck.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/25</p>
 *
 * @author tck
 * @version 1.0
 */
public interface IPlugin {

    int FROM_TNTERNAL = 0;
    int FROM_EXTERANL = 1;

    void attach(Activity proxyActivity);

    void onCreate(Bundle savedInstanceState);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onStart();

    void onRestart();

    void onResume();

    void onStop();

    void onDestroy();
}
