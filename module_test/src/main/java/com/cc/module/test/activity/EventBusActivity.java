package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;

import com.cc.library.base.entity.BaseEventBus;
import com.cc.library.base.util.Utils;
import com.cc.module.test.databinding.TestActivityModuleBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TestActivityModuleBinding mBinding = TestActivityModuleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvEventBus.setOnClickListener(view -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new BaseEventBus("app"));
                }
            }).start();
        });

        EventBus.getDefault().register(this);

    }

    @Subscribe(sticky = true)
    public void getMsg(BaseEventBus baseEventBus){
        if(baseEventBus.getType().equals("this is test home")){
            Utils.log("receive"+System.currentTimeMillis());
            EventBus.getDefault().removeStickyEvent(baseEventBus);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}