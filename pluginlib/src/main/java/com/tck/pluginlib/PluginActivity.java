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
public class PluginActivity extends Activity implements IPlugin {

    private Activity proxyActivity;

    private int mFrom = FROM_TNTERNAL;


    @Override
    public void attach(Activity proxyActivity) {
        this.proxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_TNTERNAL) {
            super.onCreate(savedInstanceState);
            this.proxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FROM_TNTERNAL) {
            super.setContentView(layoutResID);
        } else {
            proxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mFrom == FROM_TNTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_TNTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_TNTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_TNTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_TNTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_TNTERNAL) {
            super.onDestroy();
        }
    }
}
