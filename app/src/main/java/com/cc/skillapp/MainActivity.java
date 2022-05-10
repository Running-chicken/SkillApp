package com.cc.skillapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.activity.AllApplicationActivity;
import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.ChangeIconActivity;
import com.cc.skillapp.activity.CustomControlActivity;
import com.cc.skillapp.activity.ImageAvatarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LocationActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.OrganizationalStructureActivity;
import com.cc.skillapp.activity.RecyclerViewActivity;
import com.cc.skillapp.activity.RvSuspensionActivity;
import com.cc.skillapp.activity.SwipeRefreshLayoutActivity;
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
        mBinding.tvOkHttp.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OkHttpActivity.class)));
        mBinding.tvCalendar.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CalendarActivity.class)));
        mBinding.tvWvLocal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoadLocalWebActivity.class)));

        mBinding.tvRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RecyclerViewActivity.class));
            }
        });

        mBinding.tvSwiperefreshlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SwipeRefreshLayoutActivity.class));
            }
        });

        mBinding.tvImageDiejia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ImageAvatarActivity.class));
            }
        });

        mBinding.tvViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ViewPagerActivity.class));
            }
        });
        mBinding.tvRvXf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RvSuspensionActivity.class));
            }
        });
        mBinding.tvCustomControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CustomControlActivity.class));
            }
        });

        mBinding.tvOrganizational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, OrganizationalStructureActivity.class));
            }
        });


        mBinding.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LocationActivity.class));
            }
        });

        mBinding.tvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CustomControlActivity.class));
            }
        });
        mBinding.tvAllApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, AllApplicationActivity.class));
            }
        });

        mBinding.tvChangeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ChangeIconActivity.class));
            }
        });



    }

    private void initView() {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/soufun/res/app-static");
        if (!file.exists()) {
            file.mkdirs();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        checkIconSP();
    }

    private void checkIconSP() {
        SharedPreferences changeIconSP = getSharedPreferences("changeIcon",MODE_PRIVATE);
        String iconLabel = changeIconSP.getString("iconLabel","default");

        if(iconLabel.equals("default")){
            setDefaultIcon();
        }else{
            setRoundIcon();
        }
    }


    private void setRoundIcon() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".MainSplashActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName()+".SecondIconActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void setDefaultIcon(){
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName()+".MainSplashActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                packageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,getPackageName()+".SecondIconActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
