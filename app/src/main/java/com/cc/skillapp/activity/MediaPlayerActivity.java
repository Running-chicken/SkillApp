package com.cc.skillapp.activity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import static android.media.MediaPlayer.SEEK_CLOSEST;

public class MediaPlayerActivity extends BaseActivity {
    private static final String TAG = MediaPlayerActivity.class.getSimpleName();
    SurfaceView mSurfaceview;
    MediaPlayer mMediaPlayer;
    SurfaceHolder mSurfaceHolder;
    private ImageView ivPlay;
    private boolean isInitFinish = false;

    private SeekBar seekBar;
    private Timer timer;
    private LinearLayout llControl;

    Handler mHandler = new Handler();
    private TextView tvCurrentTime;
    private TextView tvTotalTime;

    private RelativeLayout rlMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        initView();

        initMediaPalyer();
        initSurfaceviewStateListener();
        registerListener();
    }



    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if(mMediaPlayer!=null){
                seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                tvCurrentTime.setText(formatTime(mMediaPlayer.getCurrentPosition()));
                mHandler.postDelayed(this,100);
            }
        }
    };


    private void registerListener() {
        rlMediaPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    //按下
                    case MotionEvent.ACTION_DOWN:
                        if(llControl.getVisibility()==View.VISIBLE){
                            llControl.setVisibility(View.GONE);
                            ivPlay.setVisibility(View.GONE);
                        }else{
                            llControl.setVisibility(View.VISIBLE);
                            ivPlay.setVisibility(View.VISIBLE);
                        }

                        break;
                }

                return true;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    tvCurrentTime.setText(formatTime(progress));
                }
            }

            // 在手指正在拖动seekBar，而手指未离开屏幕触发的方法
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateRunnable);
            }

            // 表示手指拖动seekbar完毕，手指离开屏幕会触发以下方法
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mMediaPlayer.seekTo(seekBar.getProgress(),SEEK_CLOSEST);
                    mHandler.post(updateRunnable);
                }else{
                    mMediaPlayer.seekTo(seekBar.getProgress());
                    mHandler.postDelayed(updateRunnable,2000);
                }
            }
        });


        ivPlay.setOnClickListener(mOnClick);
    }

    View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_control:
                    if(mMediaPlayer.isPlaying()){
                        mMediaPlayer.pause();
                        ivPlay.setImageResource(R.drawable.iv_play);
                    }else{
                        mMediaPlayer.start();
                        ivPlay.setImageResource(R.drawable.iv_pause);
                    }
                    break;

            }
        }
    };

    private String formatTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    private void initView() {
        mSurfaceview = findViewById(R.id.sv_video);
        ivPlay = findViewById(R.id.iv_control);
        seekBar = findViewById(R.id.seekbar_video);
        llControl = findViewById(R.id.ll_control);
        tvCurrentTime =findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        rlMediaPlayer = findViewById(R.id.rl_mediaplayer);
    }


    private void showOrHiddenController(){
        if(llControl.getVisibility() == View.VISIBLE){
            llControl.setVisibility(View.GONE);
        }else{
            llControl.setVisibility(View.VISIBLE);
        }

    };



    Uri uri;
    private void initMediaPalyer() {

        uri = Uri.parse("http://hi1grvrcg7xin1ne6w8.exp.bcevod.com/mda-mfan39qdty7adn99/mda-mfan39qdty7adn99.mp4");
        mMediaPlayer = new MediaPlayer();

    }


    private void initSurfaceviewStateListener() {
        mSurfaceHolder = mSurfaceview.getHolder();
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
                    seekBar.setMax(mMediaPlayer.getDuration());
                    tvTotalTime.setText(formatTime(mMediaPlayer.getDuration()));
                    mHandler.postDelayed(updateRunnable,100);
                    mp.start();
                }
            });

            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mediaPlayer) {

                }
            });

            mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    changeVideoSize();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**根据视频分辨率设置surfaceview宽高*/
    public void changeVideoSize() {
        int videoWidth = mMediaPlayer.getVideoWidth();
        int videoHeight = mMediaPlayer.getVideoHeight();
        int rlWidth = rlMediaPlayer.getWidth();
        int rlHeight = rlMediaPlayer.getHeight();

        //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) rlWidth,(float) videoHeight / (float) rlHeight);
        } else{
            //横屏模式下按视频高度计算放大倍数值
            max = Math.max(((float) videoWidth/(float) rlWidth),(float) videoHeight/(float) rlHeight);
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        mSurfaceview.setLayoutParams(new RelativeLayout.LayoutParams(videoWidth, videoHeight));
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

        if(timer!=null){
            timer.cancel();
        }
    }

}
