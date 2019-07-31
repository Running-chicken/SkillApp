package com.cc.skillapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LoadWebActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.ReportActivity;

import java.io.File;

public class MainActivity extends BaseActivity {

    private TextView tvFuncitonOne;
    private TextView tvWebView;
    private TextView tvCalendar;
    private TextView tvLocalResource;
    private TextView tvWv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registerListener();
    }

    private void registerListener() {
        tvFuncitonOne.setOnClickListener(mOnClick);
        tvWebView.setOnClickListener(mOnClick);
        tvCalendar.setOnClickListener(mOnClick);
        tvLocalResource.setOnClickListener(mOnClick);
        tvWv.setOnClickListener(mOnClick);
    }

    private void initView() {
        tvFuncitonOne = findViewById(R.id.tv_first_function);
        tvWebView = findViewById(R.id.tv_second_funciton);
        tvCalendar = findViewById(R.id.tv_calendar);
        tvLocalResource  =findViewById(R.id.tv_wv_local);
        tvWv =findViewById(R.id.tv_webview);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/soufun/res/app-static");
        if (!file.exists()) {
            file.mkdirs();
        }

    }

    View.OnClickListener mOnClick  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_first_function:
                    startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
                    break;
                case R.id.tv_second_funciton:
                    startActivity(new Intent(MainActivity.this, ReportActivity.class));
                    break;
                case R.id.tv_calendar:
                    startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                    break;
                case R.id.tv_wv_local:
                    startActivity(new Intent(MainActivity.this, LoadLocalWebActivity.class));
                    break;

                case R.id.tv_webview:
                    startActivity(new Intent(MainActivity.this, LoadWebActivity.class));
                    break;
            }
        }
    };
}
