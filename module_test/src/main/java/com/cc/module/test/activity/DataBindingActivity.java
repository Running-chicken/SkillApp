package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.cc.module.test.R;
import com.cc.module.test.databinding.TestDataBinding;
import com.cc.module.test.entity.TestUser;
import com.cc.module.test.entity.TestUserNew;

public class DataBindingActivity extends AppCompatActivity {

    TestDataBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_databinding);
        mBinding.setLifecycleOwner(this);

        TestUser user = new TestUser();
        user.setName("崔粲");
        mBinding.setUser(user);

        TestUserNew userNew = new TestUserNew();
        userNew.name.set("周杰伦");
        userNew.alasName.setValue("别名库里");
        mBinding.setUserNew(userNew);


        mBinding.tvClick.setOnClickListener(view -> {
            user.setName("bean真真");
            userNew.name.set("field昆凌");
            userNew.alasName.setValue("set汤神");
        });


    }


}