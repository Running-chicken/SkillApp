package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;

import com.bumptech.glide.util.Util;
import com.cc.library.base.interfaces.LifecycleListener;
import com.cc.library.base.util.LifecycleDetector;
import com.cc.library.base.util.Utils;
import com.cc.module.test.R;

public class TestLifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_lifecycle);


        LifecycleDetector.getInstance().with(this,new Test());


        findViewById(R.id.tv_glide).setOnClickListener(view -> {
            LifecycleDetector.getInstance().with(this,new Test());
        });
    }

    static class Test implements LifecycleListener{

        @Override
        public void onStart() {
            Utils.log("start");
        }

        @Override
        public void onDestroy() {
            Utils.log("destroy");
        }

        @Override
        public void onStop() {
            Utils.log("stop");
        }
    }

}