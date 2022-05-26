package com.cc.skillapp.test.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTestFragmentBinding;

public class TestFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestFragmentBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test_fragment);

        Fragment testFragment1 = new TestFragment1();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_root,testFragment1).commit();

    }
}