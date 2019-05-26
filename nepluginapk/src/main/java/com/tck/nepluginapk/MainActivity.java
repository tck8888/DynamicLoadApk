package com.tck.nepluginapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tck.pluginlib.PluginActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends PluginActivity {

    private RecyclerView recyclerView;

    private List<GroupBean> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(that, RecyclerView.VERTICAL, false));
        GroupAdapter groupAdapter = new GroupAdapter(that, list);
        recyclerView.setAdapter(groupAdapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            GroupBean groupBean = new GroupBean();
            groupBean.name = "Group" + i;
            List<ChildBean> childBeans = new ArrayList<>();
            for (int i1 = 0; i1 < 20; i1++) {
                childBeans.add(new ChildBean(groupBean.name + " child " + i1));
            }
            groupBean.childBeans = childBeans;
            list.add(groupBean);
        }
    }
}
