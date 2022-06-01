package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cc.library.base.util.RouterPath;
import com.cc.module.test.databinding.TestActivityModuleBinding;

@Route(path = RouterPath.Test.TEST_HOME)
public class TestModuleActivity extends AppCompatActivity {

    TestActivityModuleBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = TestActivityModuleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvRxjava.setOnClickListener(view -> {
            startActivity(new Intent(this,RxJavaActivity.class));
        });

        mBinding.tvSql.setOnClickListener(view -> {
            startActivity(new Intent(this,SQLiteActivity.class));
        });
    }
}