package com.cc.skillapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityMainTestBinding;
import com.cc.skillapp.utils.RouterPath;

@Route(path = RouterPath.Test.TEST_HOME)
public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainTestBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_test);
        mBinding.tvItemBinding.setOnClickListener(view -> {
            startActivity(new Intent(this,TestActivity.class));
        });

        mBinding.tvLifePeriod.setOnClickListener(view -> {
            startActivity(new Intent(this, TestLifeAActivity.class));
        });
    }
}