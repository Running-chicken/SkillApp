package com.cc.module.test.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cc.module.test.R;
import com.cc.module.test.adapter.MyFragmentStateAdapter;
import com.cc.module.test.adapter.MyVp2Adapter;
import com.cc.module.test.databinding.TestVp2Binding;
import com.cc.module.test.databinding.TestVpBinding;
import com.cc.module.test.fragment.Fragment1;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {


    TestVp2Binding mBinding;
    List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_view_pager2);
//        fragments = new ArrayList<>();

//        for(int i=0;i<5;i++){
//            String name = "fragment"+i;
//            Fragment1 fragment = new Fragment1();
//            fragment.setName(name);
//            fragments.add(fragment);
//        }
//
        List<Pic> list = new ArrayList<>();
        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png","ccccc"));
        list.add(new Pic("","ccccc"));
        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png",""));
        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png",""));
        list.add(new Pic("","ccccc"));
        list.add(new Pic("","ccccc"));


        MyVp2Adapter adapter = new MyVp2Adapter(this,list);

        mBinding.testVp.setAdapter(adapter);
        mBinding.testVp.setOffscreenPageLimit(3);


    }

    public static class Pic{
        public String url;
        public String name;
        public Pic(String url,String name){
            this.url = url;
            this.name = name;
        }

    }
}