package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.cc.module.test.R;
import com.cc.module.test.adapter.MyFragmentAdapter;
import com.cc.module.test.adapter.MyFragmentStateAdapter;
import com.cc.module.test.databinding.TestVp2Binding;
import com.cc.module.test.databinding.TestVpBinding;
import com.cc.module.test.fragment.Fragment1;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {


    TestVpBinding mBinding;
    List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_view_pager);
        fragments = new ArrayList<>();

        for(int i=0;i<5;i++){
            String name = "fragment"+i;
            Fragment1 fragment = new Fragment1();
            fragment.setName(name);
            fragments.add(fragment);
        }

//        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        MyFragmentStateAdapter adapter = new MyFragmentStateAdapter(getSupportFragmentManager(),fragments);
        mBinding.testVp.setAdapter(adapter);
        mBinding.testVp.setOffscreenPageLimit(3);


    }
}