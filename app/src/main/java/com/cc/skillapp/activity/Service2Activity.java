package com.cc.skillapp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityService2Binding;
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
//            Intent intent = new Intent();
            Intent intent = new Intent("com.cc.skillapp.my.receiver");
            intent.putExtra("params","这是静态广播");
            intent.setPackage("com.cc.skillapp");
//            intent.setComponent(new ComponentName(getPackageName(), "com.cc.skillapp.receive.MyBroadReceive"));
            sendBroadcast(intent);
        });

        mBinding.tvSendBroadcast1.setOnClickListener(view -> {
            Intent intent = new Intent("com.cc.me.dt.broadcast");
            intent.putExtra("params","这是动态广播");
            sendBroadcast(intent);
        });


    }
}