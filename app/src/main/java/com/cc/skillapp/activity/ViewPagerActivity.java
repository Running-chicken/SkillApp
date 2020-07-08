package com.cc.skillapp.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cc.skillapp.R;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vpBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
    }

    private void initView() {
        vpBanner = findViewById(R.id.vp_banner);

    }
}
