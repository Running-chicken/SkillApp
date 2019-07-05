package com.cc.skillapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LoadWebActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.ReportActivity;
import com.cc.skillapp.utils.DangerousPermissions;
import com.cc.skillapp.utils.PermissionUtils;

public class MainSplashActivity extends BaseActivity {

    private static final String[] LOCATION_STATE_STORAGE =
            { DangerousPermissions.READ_PHONE_STATE,DangerousPermissions.READ_EXTERNAL_STORAGE,DangerousPermissions.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);


    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermisssions();
    }

    /**
     * 处理权限的方法
     */
    public void requestPermisssions(){
        if (!PermissionUtils.checkPermissions(MainSplashActivity.this,LOCATION_STATE_STORAGE)){
            ActivityCompat.requestPermissions(MainSplashActivity.this,LOCATION_STATE_STORAGE, PermissionUtils.REQUEST_SPLASH_PERMISSIONS);
        }else {
            finish();
            startActivity(new Intent(mContext,MainActivity.class));
        }
    }



    /**
     * 这里需要处理授权的结果， 复写onRequestPermissionsResult方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllGranted = true;//是否全部权限授权
        for (int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_DENIED){
                isAllGranted = false;
                break;
            }
        }
        if (isAllGranted){//已全部授权
            finish();
            startActivity(new Intent(mContext,MainActivity.class));
        }else {//有未授权的权限
            PermissionUtils.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        }
    }
}
