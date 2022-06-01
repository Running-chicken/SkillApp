package com.cc.skillapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTestLife2Binding;
import com.cc.library.base.util.Utils;

public class TestLifeBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_life2);

        ActivityTestLife2Binding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test_life2);
        mBinding.tvLife2.setOnClickListener(view -> {
            startActivity(new Intent(this,TestLifeAActivity.class));
        });

        if(savedInstanceState!=null){
            Utils.log(getClass(),"onCreate bundle="+savedInstanceState.getString("save"));
        }else {
            Utils.log(getClass(),"onCreate");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.log(getClass(),"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.log(getClass(),"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.log(getClass(),"onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.log(getClass(),"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.log(getClass(),"onRestart");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Utils.log(getClass(),"onRestoreInstanceState bundle="+savedInstanceState.getString("save"));
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        Utils.log(getClass(),"onSaveInstanceState");
        outState.putString("save","had save bundle");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.log(getClass(),"onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Utils.log(getClass(),"onNewIntent" + intent.getStringExtra("param"));
    }
}