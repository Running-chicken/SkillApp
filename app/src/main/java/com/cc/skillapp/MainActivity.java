package com.cc.skillapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.activity.AllApplicationActivity;
import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.ChangeIconActivity;
import com.cc.skillapp.activity.ImageAvatarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LocationActivity;
import com.cc.skillapp.activity.MediaPlayerActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.OrganizationalStructureActivity;
import com.cc.skillapp.activity.RecyclerViewActivity;
import com.cc.skillapp.activity.RvSuspensionActivity;
import com.cc.skillapp.activity.ViewPagerActivity;
import com.cc.skillapp.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends BaseActivity {


    ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        initView();
        registerListener();
    }

    private void registerListener() {
        //okhttp
        mBinding.tvOkHttp.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OkHttpActivity.class)));
        //日历
        mBinding.tvCalendar.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CalendarActivity.class)));
        //webView加载本地资源
        mBinding.tvWvLocal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoadLocalWebActivity.class)));
        //recyclerView下拉刷新
        mBinding.tvRecyclerview.setOnClickListener(view -> startActivity(new Intent(mContext, RecyclerViewActivity.class)));


        mBinding.tvImageDiejia.setOnClickListener(view -> startActivity(new Intent(mContext, ImageAvatarActivity.class)));

        mBinding.tvViewpager.setOnClickListener(view -> startActivity(new Intent(mContext, ViewPagerActivity.class)));
        mBinding.tvRvXf.setOnClickListener(view -> startActivity(new Intent(mContext, RvSuspensionActivity.class)));


        mBinding.tvOrganizational.setOnClickListener(view -> startActivity(new Intent(mContext, OrganizationalStructureActivity.class)));


        mBinding.tvLocation.setOnClickListener(view -> startActivity(new Intent(mContext, LocationActivity.class)));

        mBinding.tvVideo.setOnClickListener(view -> startActivity(new Intent(mContext, MediaPlayerActivity.class)));

        mBinding.tvAllApp.setOnClickListener(view -> startActivity(new Intent(mContext, AllApplicationActivity.class)));

        mBinding.tvChangeIcon.setOnClickListener(view -> startActivity(new Intent(mContext, ChangeIconActivity.class)));



    }

    private void initView() {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/soufun/res/app-static");
        if (!file.exists()) {
            file.mkdirs();
        }

    }




}
