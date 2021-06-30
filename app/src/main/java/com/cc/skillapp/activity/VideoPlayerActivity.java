package com.cc.skillapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.ViewPagerAdapter;
import com.cc.skillapp.utils.StringUtils;
import com.cc.skillapp.view.InfiniteShufflingViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.net.ConnectivityManager.TYPE_WIFI;

public class VideoPlayerActivity extends BaseActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(StringUtils.getNetWorkType(mContext)==1){
                    videoView.start();
                }
            }
        });

        videoView.setMediaController(new MediaController(VideoPlayerActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int netType = StringUtils.getNetWorkType(mContext);
        if (netType == -1) {
            new AlertDialog.Builder(mContext).setMessage("无网络")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).create().show();
        } else if (netType == TYPE_WIFI) {
            setVideoView();
        } else {
            new AlertDialog.Builder(mContext).setMessage("使用移动网络")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setVideoView();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).create().show();
        }


    }

    private void setVideoView(){
        Uri uri = Uri.parse("http://hi1grvrcg7xin1ne6w8.exp.bcevod.com/mda-keipcv6wknym8z1m/mda-keipcv6wknym8z1m.mp4");
        videoView.setVideoURI(uri);
        videoView.start();
    }


    private void initView() {
        videoView = findViewById(R.id.video_view);

    }

    private void initData() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.suspend();
    }
}
