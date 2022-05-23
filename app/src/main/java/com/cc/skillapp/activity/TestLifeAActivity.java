package com.cc.skillapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTest2Binding;
import com.cc.skillapp.utils.Utils;

public class TestLifeAActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTest2Binding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test2);
        mBinding.tvStart.setOnClickListener(view ->
                {
                    Intent intent = new Intent();
                    intent.setClass(this, TestLifeAActivity.class);
                    intent.putExtra("param","singleTop");
                    startActivity(intent);
                }

        );

        mBinding.tvStart1.setOnClickListener(view -> {
            Intent intent = new Intent(this, TestLifeBActivity.class);
            startActivity(intent);
        });

        if(savedInstanceState!=null){
            Utils.log(getClass(),"onCreate bundle="+savedInstanceState.getString("save"));
        }else {
            Utils.log(getClass(),"onCreate");
        }
    }

    @Override
    protected void onResume() {
        Utils.log(getClass(),"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Utils.log(getClass(),"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Utils.log(getClass(),"onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Utils.log(getClass(),"onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Utils.log(getClass(),"onRestart");
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Utils.log(getClass(),"onRestoreInstanceState bundle="+savedInstanceState.getString("save"));
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Utils.log(getClass(),"onSaveInstanceState");
        outState.putString("save","had save bundle");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Utils.log(getClass(),"onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Utils.log(getClass(),"onNewIntent" + intent.getStringExtra("param"));
        super.onNewIntent(intent);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Utils.log(getClass(),"onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}