package com.tck.dynamicloadapk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tck.pluginlib.PluginManage;
import com.tck.pluginlib.ProxyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = copyAssetAndWrite(MainActivity.this, "aa.apk");
                PluginManage.getInstance().loadApk(s);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProxyActivity.class);
                intent.putExtra("className", PluginManage.getInstance().getPluginAPK().packageInfo.activities[0].name);
                startActivity(intent);
            }
        });

    }

    public String copyAssetAndWrite(Context context, String fileName) {
        try {

            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            File outFile = new File(cacheDir, fileName);
            if (outFile.exists()) {
                outFile.delete();
            }
            if (!outFile.exists()) {
                boolean newFile = outFile.createNewFile();
                if (newFile) {
                    InputStream open = context.getAssets().open(fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                    byte[] bytes = new byte[open.available()];
                    int byteCount;
                    while ((byteCount = open.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, byteCount);
                    }
                    fileOutputStream.flush();
                    open.close();
                    fileOutputStream.close();
                    Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    return outFile.getAbsolutePath();
                }
            } else {
                Toast.makeText(context, "文件已经存在", Toast.LENGTH_SHORT).show();
                return outFile.getAbsolutePath();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
