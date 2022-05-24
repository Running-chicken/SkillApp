package com.cc.skillapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityServiceBinding;
import com.cc.skillapp.service.MyIntentService;
import com.cc.skillapp.service.MyService;
import com.cc.skillapp.utils.Utils;

public class ServiceActivity extends BaseActivity {

    MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityServiceBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_service);
        mBinding.tvStartService.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        mBinding.tvCloseService.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        mBinding.tvBindService.setOnClickListener(view -> {
            Intent intent = new Intent(this,MyService.class);
            bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        });

        mBinding.tvUnbindService.setOnClickListener(view -> {
            unbindService(serviceConnection);
            if(myService!=null){
                myService = null;
            }
        });

        mBinding.tvDoSomething.setOnClickListener(view -> {
            startService(new Intent(this, MyIntentService.class).putExtra("params","this.is.my.test.b"));
        });

        mBinding.tvIntentService.setOnClickListener(view -> {
            startService(new Intent(this, MyIntentService.class).putExtra("params","wiiwafefeafefeaooji"));
        });


    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myService = ((MyService.MyBinder) iBinder).getService();
//            Utils.log("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
//            Utils.log("onServiceDisconnected");
        }
    };

}