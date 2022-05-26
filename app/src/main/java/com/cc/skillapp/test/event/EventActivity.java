package com.cc.skillapp.test.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.LruCache;
import android.view.MotionEvent;
import android.view.View;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityEventBinding;
import com.cc.skillapp.utils.Utils;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEventBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_event);
        mBinding.myLayout.setOnClickListener(view -> {
            Utils.log(getClass(),"myLayout onClick");
        });

        mBinding.myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Utils.log(getClass(),"myLayout onTouch");
                return false;
            }
        });

        mBinding.btButton.setOnClickListener(view -> {
            Utils.log(getClass(),"button onClick");
        });

    }
}