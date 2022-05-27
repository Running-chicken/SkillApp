package com.cc.skillapp.test.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivitySurfaceBinding;

public class SurfaceActivity extends AppCompatActivity {

    ActivitySurfaceBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_surface);

    }


}