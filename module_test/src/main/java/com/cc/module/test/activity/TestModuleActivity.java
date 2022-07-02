package com.cc.module.test.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.library.base.entity.BaseEventBus;
import com.cc.library.base.util.RouterPath;
import com.cc.library.base.util.WebViewUtils;
import com.cc.module.test.databinding.TestActivityModuleBinding;
import com.cc.module.test.entity.TestParcel;

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

        mBinding.tvGlide.setOnClickListener(view -> {
            startActivity(new Intent(this,TestLifecycleActivity.class));
        });

        mBinding.tvSave.setOnClickListener(view -> {
            startActivity(new Intent(this,SaveActivity.class));
        });

        mBinding.tvEventBusSticky.setOnClickListener(view -> {
            EventBus.getDefault().postSticky(new BaseEventBus("this is test home"));
        });

        mBinding.tvDatabinding.setOnClickListener(view -> {
            startActivity(new Intent(this,DataBindingActivity.class));
        });

        mBinding.tvTextBp.setOnClickListener(view -> {
            startActivity(new Intent(this,ViewPagerActivity.class));
        });

        mBinding.tvTextVp2.setOnClickListener(view -> {
            startActivity(new Intent(this,ViewPager2Activity.class));
        });

        mBinding.tvTextVp2Frag.setOnClickListener(view -> {
            startActivity(new Intent(this,ViewPager2FragmentActivity.class));
        });

        mBinding.tvTestLv.setOnClickListener(view -> {
            startActivity(new Intent(this, MyRvActivity.class));
        });

        mBinding.tvMesure.setOnClickListener(view -> {

            startActivity(new Intent(this, ViewStubActivity.class).putExtra("data",new TestParcel(1,"cuican")));
        });

        mBinding.tvWebviewProcess.setOnClickListener(view -> {
            Intent intent = new Intent(this,WebViewProcessActivity.class);
            intent.putExtra("url","https://www.baidu.com");
            startActivityForResult(intent,111);

        });

        mBinding.tvThreadPool.setOnClickListener(view -> {
            Intent intent = new Intent(this,ThreadPoolActivity.class);
            startActivity(intent);

        });

        mBinding.tvThreadCreate.setOnClickListener(view -> {
            Intent intent = new Intent(this,ThreadCreateActivity.class);
            startActivity(intent);

        });


//        LeakThread leakThread = new LeakThread();
//        leakThread.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        WebViewUtils.getInstance().init();
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
//        Utils.log(getClass(),Thread.currentThread().getName()+"fun1");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}