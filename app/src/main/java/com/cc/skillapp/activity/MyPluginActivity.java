package com.cc.skillapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cc.plugin_core.PluginManager;
import com.cc.plugin_core.ProxyActivity;
import com.cc.skillapp.databinding.ActivityMyPluginBinding;

public class MyPluginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyPluginBinding binding = ActivityMyPluginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String path = getExternalFilesDir(null).getAbsolutePath()+"/plugin-debug.apk";
        PluginManager.getInstance().loadPlugin(this,path);

        binding.tvSkip.setOnClickListener(view -> {
            // 跳转到 plugin_core 模块的代理 Activity
            // 首先要获取 " 插件 " 模块中的入口 Activity 类
            Intent intent = new Intent(this, ProxyActivity.class);

            // 获取  " 插件 " 模块中的 Activity 数组信息
            ActivityInfo[] activityInfos = PluginManager.getInstance().getmPackageInfo().activities;

            // 获取的插件包中的 Activity 不为空 , 才进行界面跳转
            if (activityInfos.length > 0) {

                // 这里取插件包中的第 0 个 Activity
                // 次序就是在 AndroidManifest.xml 清单文件中定义 Activity 组件的次序
                // 必须将 Launcher Activity 定义在第一个位置
                // 不能在 Launcher Activity 之前定义 Activity 组件
                // 传入的是代理的目标组件的全类名
                intent.putExtra("CLASS_NAME", activityInfos[0].name);
                startActivity(intent);
            }
        });


    }
}