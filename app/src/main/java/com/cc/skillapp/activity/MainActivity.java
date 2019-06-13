package com.cc.skillapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.skillapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvFuncitonOne;
    private TextView tvCalendar;


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
    }

    private void initView() {
        tvFuncitonOne = findViewById(R.id.tv_first_function);
        tvCalendar = findViewById(R.id.tv_calendar);
    }

    View.OnClickListener mOnClick  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_first_function:
                    startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
                    break;
                case R.id.tv_calendar:
                    startActivity(new Intent(MainActivity.this,CalendarActivity.class));
                    break;
            }
        }
    };
}
