package com.cc.skillapp.activity;

import android.os.Bundle;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.view.MyShowViewLayout;

public class CustomControlActivity extends BaseActivity {

    MyShowViewLayout myShowViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_control);

        myShowViewLayout = findViewById(R.id.msv_test);
        myShowViewLayout.setRightText("我是你大爷");
    }
}
