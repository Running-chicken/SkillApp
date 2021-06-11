package com.cc.skillapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.activity.CalendarActivity;
import com.cc.skillapp.activity.CustomControlActivity;
import com.cc.skillapp.activity.ImageAvatarActivity;
import com.cc.skillapp.activity.LoadLocalWebActivity;
import com.cc.skillapp.activity.LoadWebActivity;
import com.cc.skillapp.activity.LocationActivity;
import com.cc.skillapp.activity.MediaPlayerActivity;
import com.cc.skillapp.activity.OkHttpActivity;
import com.cc.skillapp.activity.OrganizationalStructureActivity;
import com.cc.skillapp.activity.RecyclerViewActivity;
import com.cc.skillapp.activity.ReportActivity;
import com.cc.skillapp.activity.RvSuspensionActivity;
import com.cc.skillapp.activity.SwipeRefreshLayoutActivity;
import com.cc.skillapp.activity.VideoPlayerActivity;
import com.cc.skillapp.activity.ViewPagerActivity;

import java.io.File;

public class MainActivity extends BaseActivity {

    private TextView tvFuncitonOne;
    private TextView tvWebView;
    private TextView tvCalendar;
    private TextView tvLocalResource;
    private TextView tvWv;
    private TextView tvRecyclerview;
    private TextView tvSwipeRefreshLayout;
    private TextView tvImageDiejia;
    private TextView tvViewPager;
    private TextView tvXf;
    private TextView tvCustomContral;
    private TextView tvOrgizational;
    private TextView tvLocation;
    private TextView tvVideo;


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
        tvRecyclerview.setOnClickListener(mOnClick);
        tvSwipeRefreshLayout.setOnClickListener(mOnClick);
        tvImageDiejia.setOnClickListener(mOnClick);
        tvViewPager.setOnClickListener(mOnClick);
        tvXf.setOnClickListener(mOnClick);
        tvCustomContral.setOnClickListener(mOnClick);
        tvOrgizational.setOnClickListener(mOnClick);
        tvLocation.setOnClickListener(mOnClick);
        tvVideo.setOnClickListener(mOnClick);
    }

    private void initView() {
        tvFuncitonOne = findViewById(R.id.tv_first_function);
        tvWebView = findViewById(R.id.tv_second_funciton);
        tvCalendar = findViewById(R.id.tv_calendar);
        tvLocalResource  =findViewById(R.id.tv_wv_local);
        tvWv =findViewById(R.id.tv_webview);
        tvRecyclerview = findViewById(R.id.tv_recyclerview);
        tvSwipeRefreshLayout = findViewById(R.id.tv_swiperefreshlayout);
        tvImageDiejia = findViewById(R.id.tv_image_diejia);
        tvViewPager = findViewById(R.id.tv_viewpager);
        tvXf = findViewById(R.id.tv_rv_xf);
        tvCustomContral = findViewById(R.id.tv_custom_control);
        tvOrgizational = findViewById(R.id.tv_organizational);
        tvLocation = findViewById(R.id.tv_location);
        tvVideo = findViewById(R.id.tv_video);

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
                case R.id.tv_recyclerview:
                    startActivity(new Intent(mContext, RecyclerViewActivity.class));
                    break;
                case R.id.tv_swiperefreshlayout:
                    startActivity(new Intent(mContext, SwipeRefreshLayoutActivity.class));
                    break;
                case R.id.tv_image_diejia:
                    startActivity(new Intent(mContext, ImageAvatarActivity.class));
                    break;
                case R.id.tv_viewpager:
                    startActivity(new Intent(mContext, ViewPagerActivity.class));


                    break;
                case R.id.tv_rv_xf:
                    startActivity(new Intent(mContext, RvSuspensionActivity.class));
                    break;
                case R.id.tv_custom_control:
                    startActivity(new Intent(mContext, CustomControlActivity.class));
                    break;
                case R.id.tv_organizational:
                    startActivity(new Intent(mContext, OrganizationalStructureActivity.class));
                    break;
                case R.id.tv_location:
                    startActivity(new Intent(mContext, LocationActivity.class));
                    break;
                case R.id.tv_video:
                    startActivity(new Intent(mContext, MediaPlayerActivity.class));

                    break;
            }
        }
    };
}
