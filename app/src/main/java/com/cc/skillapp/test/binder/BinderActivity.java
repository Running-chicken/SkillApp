package com.cc.skillapp.test.binder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityBinderBinding;
import com.example.library_base.util.Utils;

public class BinderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBinderBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_binder);
        mBinding.tvBinder.setOnClickListener(view -> {
            Intent intent = new Intent(this,BinderService.class);
            bindService(intent,connection,BIND_AUTO_CREATE);
        });

        mBinding.tvStart.setOnClickListener(view -> {
            Utils.log("点击 start");
            Intent intent = new Intent(this, BinderService.class);
            startService(intent);
        });

        mBinding.tvUnBinder.setOnClickListener(view -> {
            unbindService(connection);
        });

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyInterface myInterface = MyBinder.asInterface(iBinder);
            Utils.log("输出结果是："+myInterface.add(1,2));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}