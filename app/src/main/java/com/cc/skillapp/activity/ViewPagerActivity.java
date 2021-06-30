package com.cc.skillapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.ViewPagerAdapter;
import com.cc.skillapp.view.InfiniteShufflingViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {

    private InfiniteShufflingViewPager vpBanner;

    private List<String> mList;

    private ViewPagerAdapter viewPagerAdapter;

    private long startTime,endTime;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        initData();

    }

    private void initView() {
        vpBanner = findViewById(R.id.vp_banner);
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png");
        mList.add("http://imgwcs3.soufunimg.com/news/2020_07/21/bea05966-1078-4c1a-90fb-8170dd5c9c67.png");
        mList.add("http://imgwcs3.soufunimg.com/news/2020_07/21/e7f5a283-5098-485d-bd38-8243b55a6ae5.png");
        mList.add("http://imgwcs3.soufunimg.com/news/2020_07/21/a8e54309-9a8b-4966-9ddc-4339328f2a6d.png");

        viewPagerAdapter = new ViewPagerAdapter(mContext,mList);
        vpBanner.setAdapter(viewPagerAdapter);

        vpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(mHandler==null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    endTime = System.currentTimeMillis();
                    if(endTime-startTime>=2000){
                        vpBanner.setCurrentItem(vpBanner.getCurrentItem()+1);
                        if(mHandler!=null){
                            mHandler.sendEmptyMessageDelayed(0,3000);
                        }
                    }
                }
            };

            new Thread(){
                @Override
                public void run() {
                    if(mHandler!=null){
                        mHandler.sendEmptyMessageDelayed(0,3000);
                    }
                }
            }.start();

            vpBanner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if(mHandler!=null){
                                mHandler.removeCallbacksAndMessages(null);
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            if(mHandler!=null){
                                mHandler.sendEmptyMessageDelayed(0,3000);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if(mHandler!=null){
                                mHandler.sendEmptyMessageDelayed(0,3000);
                            }
                            break;

                    }

                    return false;
                }
            });

        }


    }


}
