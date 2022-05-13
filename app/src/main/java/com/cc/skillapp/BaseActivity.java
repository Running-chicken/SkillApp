package com.cc.skillapp;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cc.skillapp.utils.PermissionUtils;
import com.cc.skillapp.utils.StatusBarUtils;

public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;
    protected MyApplication mApp;

    public BaseActivity(){
        this.mContext = this;
        mApp = MyApplication.getSelf();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getSelf().push(this);
        StatusBarUtils.setStatusBarTextColorDark(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mApp!=null){
            mApp.pull(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }
}
