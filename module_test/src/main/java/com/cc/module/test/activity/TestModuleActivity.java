package com.cc.module.test.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.library.base.entity.BaseEventBus;
import com.cc.library.base.util.RouterPath;
import com.cc.library.base.util.Utils;
import com.cc.module.test.databinding.TestActivityModuleBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = RouterPath.Test.TEST_HOME)
public class TestModuleActivity extends AppCompatActivity {

    TestActivityModuleBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);


        mBinding = TestActivityModuleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvRxjava.setOnClickListener(view -> {
            startActivity(new Intent(this,RxJavaActivity.class));
        });

        mBinding.tvSql.setOnClickListener(view -> {
            startActivity(new Intent(this,SQLiteActivity.class));
        });

        mBinding.tvRxjava2.setOnClickListener(view -> {
            startActivity(new Intent(this,RxJava2Activity.class));
        });

        mBinding.tvView.setOnClickListener(view -> {
            startActivity(new Intent(this,SkillViewActivity.class));
        });

        mBinding.tvEventBus.setOnClickListener(view -> {
            startActivity(new Intent(this,EventBusActivity.class));
        });

        EventBus.getDefault().postSticky(new BaseEventBus("this is test home"));

        LeakThread leakThread = new LeakThread();
        leakThread.start();
    }



    class LeakThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventBus(BaseEventBus baseEventBus){
        Utils.log(getClass(),Thread.currentThread().getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}