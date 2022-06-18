package com.cc.module.test.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cc.module.test.R;
import com.cc.module.test.adapter.MyVp2Adapter;
import com.cc.module.test.adapter.MyVp2FragmentAdapter;
import com.cc.module.test.databinding.TestVp2Binding;
import com.cc.module.test.fragment.Fragment1;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2FragmentActivity extends AppCompatActivity {


    TestVp2Binding mBinding;
    List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_view_pager2);
        fragments = new ArrayList<>();




        MyVp2FragmentAdapter adapter = new MyVp2FragmentAdapter(this);
        for(int i=0;i<5;i++){
            String name = "fragment"+i;
            Fragment1 fragment = new Fragment1();
            fragment.setName(name);
            adapter.addFragment(fragment);
        }

        mBinding.testVp.setAdapter(adapter);
        mBinding.testVp.setOffscreenPageLimit(3);


    }

}