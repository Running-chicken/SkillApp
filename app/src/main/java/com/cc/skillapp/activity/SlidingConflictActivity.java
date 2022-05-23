package com.cc.skillapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cc.skillapp.R;
import com.cc.skillapp.adapter.Vp2Adapter;
import com.cc.skillapp.databinding.ActivitySlidingConflictBinding;
import com.cc.skillapp.fragment.FragmentTest1;

import java.util.ArrayList;
import java.util.List;

public class SlidingConflictActivity extends AppCompatActivity {

    ActivitySlidingConflictBinding mBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sliding_conflict);


        FragmentTest1 fragment1 = new FragmentTest1();
        FragmentTest1 fragment2 = new FragmentTest1();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        List<String> titles = new ArrayList<>();
        titles.add("title1");
        titles.add("title2");
        Vp2Adapter adapter = new Vp2Adapter(this,fragments);
        mBinding.vpConflict.setAdapter(adapter);


    }
}