package com.cc.skillapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.utils.StringUtils;

import java.io.IOException;

import static android.net.ConnectivityManager.TYPE_WIFI;

public class MediaPlayerActivity extends BaseActivity {
    private static final String TAG = MediaPlayerActivity.class.getSimpleName();
    SurfaceView mVideoPlaySurfaceview;
    MediaPlayer mMediaPlayer;
    SurfaceHolder mSurfaceHolder;
    private ImageView mStartAndStop;
    private boolean isInitFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        initView();

        initMediaPalyer();
        initSurfaceviewStateListener();
    }

    private void initView() {
        mVideoPlaySurfaceview = findViewById(R.id.sv_video);
        mStartAndStop = findViewById(R.id.iv_control);
    }




    Uri uri;
    private void initMediaPalyer() {

        uri = Uri.parse("http://hi1grvrcg7xin1ne6w8.exp.bcevod.com/mda-keipcv6wknym8z1m/mda-keipcv6wknym8z1m.mp4");
        mMediaPlayer = new MediaPlayer();

    }


    private void initSurfaceviewStateListener() {
        mSurfaceHolder = mVideoPlaySurfaceview.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(holder);//给mMediaPlayer添加预览的SurfaceHolder
                setPlayVideo(uri);//添加播放视频的路径
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "surfaceChanged触发: width=" + width + "height" + height);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void setPlayVideo(Uri uri) {
        try {
            mMediaPlayer.setDataSource(mContext,uri);//
            mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);//缩放模式
            mMediaPlayer.setLooping(true);//设置循环播放
            mMediaPlayer.prepareAsync();//异步准备
//            mMediaPlayer.prepare();//同步准备,因为是同步在一些性能较差的设备上会导致UI卡顿
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //准备完成回调
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isInitFinish = true;
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPlay(){
        if (!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
    }

    private void stopPlay(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
    }

    private void pausePlay(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }

    private void seekTo(int time){
        mMediaPlayer.seekTo(time);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
