package com.cc.skillapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LoadWebActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.ReportActivity;

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
        tvCalendar.setOnClickListener(mOnClick);
        tvWebView.setOnClickListener(mOnClick);
        tvLocalResource.setOnClickListener(mOnClick);
        tvWv.setOnClickListener(mOnClick);
    }

    private void initView() {
        tvFuncitonOne = findViewById(R.id.tv_first_function);
        tvCalendar = findViewById(R.id.tv_calendar);
        tvWebView = findViewById(R.id.tv_second_funciton);
        tvLocalResource  =findViewById(R.id.tv_third_function);
        tvWv =findViewById(R.id.tv_webview);
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
                case R.id.tv_third_function:
                    startActivity(new Intent(MainActivity.this, LoadLocalWebActivity.class));
                    break;
                case R.id.tv_webview:
                    startActivity(new Intent(MainActivity.this, LoadWebActivity.class));
                    break;
            }
        }
    };
}
