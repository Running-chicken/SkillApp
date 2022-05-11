package com.cc.skillapp.activity;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;

public class ChangeIconActivity extends BaseActivity {

    TextView tvDefault;
    TextView tvIconOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);
        initView();
        registerListener();
    }


    private void registerListener() {
        tvDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeChangeIcon("default");
            }
        });

        tvIconOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeChangeIcon("newIcon");
            }
        });
    }


    private void initView() {
        tvDefault = findViewById(R.id.tv_default);
        tvIconOne = findViewById(R.id.tv_change_icon);

    }


    private void changeChangeIcon(String newLabel) {
        Toast.makeText(mContext,newLabel,Toast.LENGTH_LONG).show();
        SharedPreferences changeIconSP = getSharedPreferences("changeIcon", MODE_PRIVATE);
        String iconLabel = changeIconSP.getString("iconLabel","default");
        if (!iconLabel.equals(newLabel)) {//1未关闭要显示   2记录版本号小于当前版本号
            SharedPreferences.Editor editor = changeIconSP.edit();
            editor.putString("iconLabel",newLabel);
            editor.apply();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkIconSP();
    }
}
