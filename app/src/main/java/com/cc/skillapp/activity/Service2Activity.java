package com.cc.skillapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityService2Binding;
import com.cc.skillapp.receive.MyBroadReceive;
import com.cc.skillapp.service.MyService;

public class Service2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityService2Binding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_service2);
        mBinding.tvStopService.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        mBinding.tvSendBroadcast.setOnClickListener(view -> {
            Intent intent = new Intent("com.cc.me.broadcast");
            intent.putExtra("params","this is my broadcast");
            intent.setComponent(new ComponentName(getPackageName(), "com.cc.skillapp.receive.MyBroadReceive"));
            sendBroadcast(intent);
        });


    }
}