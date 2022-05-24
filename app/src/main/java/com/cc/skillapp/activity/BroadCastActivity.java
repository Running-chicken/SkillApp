package com.cc.skillapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityBroadCastBinding;
import com.cc.skillapp.receive.MyBroadReceive;

public class BroadCastActivity extends AppCompatActivity {

    MyBroadReceive receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBroadCastBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_broad_cast);
        mBinding.tvSendBroadcast.setOnClickListener(view -> {
            startActivity(new Intent(this,Service2Activity.class));
        });

//        IntentFilter intentFilter = new IntentFilter("com.cc.me.broadcast");
//        receive = new MyBroadReceive();
//        registerReceiver(receive,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receive);
    }
}