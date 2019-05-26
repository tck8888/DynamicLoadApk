package com.tck.nepluginapk;

import android.os.Bundle;

import com.tck.pluginlib.PluginActivity;


/**
 * <p>description:</p>
 * <p>created on: 2019/5/26</p>
 *
 * @author tck
 * @version 1.0
 */
public class SecondActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
