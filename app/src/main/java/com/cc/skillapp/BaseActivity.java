package com.cc.skillapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.cc.skillapp.utils.PermissionUtils;

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
