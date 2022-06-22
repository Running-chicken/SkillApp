package com.cc.skillapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.skillapp.R;
import com.cc.skillapp.contentprovider.ContentObserverActivity;
import com.cc.skillapp.contentprovider.ContentProviderActivity;
import com.cc.skillapp.databinding.ActivityMainTestBinding;
import com.cc.skillapp.test.HandlerActivity;
import com.cc.skillapp.test.event.EventActivity;
import com.cc.skillapp.test.fragment.TestFragmentActivity;
import com.cc.skillapp.test.image.ImageActivity;
import com.cc.skillapp.test.view.SurfaceActivity;
import com.cc.library.base.util.RouterPath;

@Route(path = RouterPath.Main.MAIN_TEST)
public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainTestBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_test);
        mBinding.tvItemBinding.setOnClickListener(view -> {
            startActivity(new Intent(this,TestActivity.class));
        });

        mBinding.tvLifePeriod.setOnClickListener(view -> {
            Intent intent = new Intent();
            //跨进程
//            intent.setAction("test.cc.demo.action.param");
//            intent.setData(Uri.parse("myscheme://cc.com:8888"));

            //当前app
            intent.setAction("test.demo.action.life.a");

            startActivity(intent);
        });

        mBinding.tvService.setOnClickListener(view -> {
            startActivity(new Intent(this,ServiceActivity.class));
        });

        mBinding.tvBroadcast.setOnClickListener(view -> {
            startActivity(new Intent(this, BroadCastActivity.class));
        });

        mBinding.tvSqlite.setOnClickListener(view -> {
            startActivity(new Intent(this, ContentProviderActivity.class));
        });

        mBinding.tvContentObserver.setOnClickListener(view -> {
            startActivity(new Intent(this, ContentObserverActivity.class));
        });


        mBinding.tvHandler.setOnClickListener(view -> {
            startActivity(new Intent(this, HandlerActivity.class));
        });

        mBinding.tvFragment.setOnClickListener(view -> {
            startActivity(new Intent(this, TestFragmentActivity.class));
        });
        mBinding.tvEvent.setOnClickListener(view -> {
            startActivity(new Intent(this, EventActivity.class));
        });
        mBinding.tvImage.setOnClickListener(view -> {
            startActivity(new Intent(this, ImageActivity.class));
        });

        mBinding.tvSurface.setOnClickListener(view -> {
            startActivity(new Intent(this, SurfaceActivity.class));
        });





    }
}