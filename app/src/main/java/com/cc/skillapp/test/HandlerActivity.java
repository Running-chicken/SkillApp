package com.cc.skillapp.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import com.bumptech.glide.GenericTranscodeRequest;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityHandlerBinding;
import com.cc.skillapp.utils.Utils;

import java.lang.reflect.Field;

public class HandlerActivity extends AppCompatActivity {

    ActivityHandlerBinding mBinding;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Utils.log(getClass(),"this "+message.what);
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_handler);
        mBinding.tvHandler.setOnClickListener(view -> {
            Message message = new Message();
            message.what = 1;
            handler.sendMessageDelayed(message,3000);
            Message message1 = new Message();
            message1.what = 2;
            handler.sendMessageDelayed(message1,1000);


        });

        mBinding.tvSonThread.setOnClickListener(view -> {
            createMyThread();
        });


        mBinding.tvHandlerThread.setOnClickListener(view ->{
            handlerThread();
        });

        mBinding.tvSyncBarrier.setOnClickListener(view -> {
            startActivity(new Intent(this,MessageQueueActivity.class));
        });

    }


    Handler sonHandler;
    private void createMyThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                sonHandler = new Handler(Looper.myLooper(),new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        Utils.log(getClass(),"this 100");
                        return false;
                    }
                });
                Looper.loop();
            }
        }).start();

    }

    Handler mUIHandler;
    private void handlerThread(){
        DownTask handlerThread = new DownTask("test");
        handlerThread.start();
        mUIHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Utils.log(getClass(),Thread.currentThread().getName()+"开启");
                        break;
                    case 1:
                        Utils.log(getClass(),Thread.currentThread().getName()+"下载完成");
                        break;
                }
            }
        };
        handlerThread.setHandler(mUIHandler);
        handlerThread.startTask();
    }





}