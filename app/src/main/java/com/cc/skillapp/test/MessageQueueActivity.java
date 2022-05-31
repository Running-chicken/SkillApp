package com.cc.skillapp.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityMessageQueueBinding;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class MessageQueueActivity extends AppCompatActivity {

    ActivityMessageQueueBinding mBinding;
    private int count=0;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message_queue);
        initHandlerThread();
        mBinding.tvSendNormalMsg.setOnClickListener(view -> {
            if(mHandler==null){
                mHandler = threadManager.getHandler();
            }
            Message message = new Message();
            message.obj = "同步消息"+count;
            mHandler.sendMessageDelayed(message,5000);

        });

        mBinding.tvSendSyncMsg.setOnClickListener(view -> {
            if(mHandler==null){
                mHandler = threadManager.getHandler();
            }
            Message message = new Message();
            message.obj = "异步消息"+count;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                message.setAsynchronous(true);
            }
            mHandler.sendMessageDelayed(message,5000);
        });

        mBinding.tvSendSyncBarrier.setOnClickListener(view -> {
            if(mHandler==null){
                mHandler = threadManager.getHandler();
            }

            threadManager.postSyncBarrier();
        });

        mBinding.tvRemoveSyncBarrier.setOnClickListener(view -> {
            if(mHandler==null){
                mHandler = threadManager.getHandler();
            }

            threadManager.removeSyncBarrier();
        });


        startPrint();

    }

    private void startPrint(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mainHandler.sendEmptyMessage(1);
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    Handler mainHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            printMessageQueue();
            return false;
        }
    });



    @Override
    protected void onStop() {
        super.onStop();
        if(timer!=null){
            timer.cancel();
        }
    }

    HandlerManager threadManager;
    Handler mHandler;
    private void initHandlerThread(){
        threadManager = new HandlerManager();
        threadManager.main();
    }



    private void printMessageQueue(){
        if(mHandler==null){
            return;
        }
        count++;
        StringBuilder stringBuilder = new StringBuilder();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            MessageQueue queue = mHandler.getLooper().getQueue();
            try {
                Field messagesField = queue.getClass().getDeclaredField("mMessages");
                messagesField.setAccessible(true);
                Message message = (Message) messagesField.get(queue);
                if(message==null){
                    stringBuilder.append("no message");
                }
                while (message!=null){
                    String msg = printMessage(message);
                    stringBuilder.append(msg).append("\n");
                    Field nextMessage = Message.class.getDeclaredField("next");
                    nextMessage.setAccessible(true);
                    message = (Message) nextMessage.get(message);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.tvShowQueue.setText(stringBuilder.toString());
                    }
                });

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }


        }


    }

    private String printMessage(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        if(message.getTarget()!=null){
            stringBuilder.append("obj=").append(message.obj).append("target=").append(message.getTarget()!=null ? "handler" : "null");
        }else{
            stringBuilder.append("屏障消息");
        }

        return stringBuilder.toString();
    }



}