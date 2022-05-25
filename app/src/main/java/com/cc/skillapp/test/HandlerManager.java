package com.cc.skillapp.test;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

import com.cc.skillapp.utils.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {

    private Handler mHandler;

    public Handler getHandler(){
        return mHandler;
    }

    public void main() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mHandler = new MyHandler(Looper.myLooper());
                Looper.loop();
            }
        }).start();
    }

    class MyHandler extends Handler{
        MyHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Utils.log(getClass(),"msg is:"+msg.obj);
        }
    }

    void sendAsyncMessage(){
        Message message = new Message();
        message.what=1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            message.setAsynchronous(true);
        }
        mHandler.sendMessage(message);
    }

    private List<Integer> mTokens = new ArrayList<>();
    void postSyncBarrier(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                MessageQueue queue = mHandler.getLooper().getQueue();
                Method postSyncBarrier = null;
                postSyncBarrier = queue.getClass().getDeclaredMethod("postSyncBarrier");
                postSyncBarrier.setAccessible(true);
                mTokens.add((int) postSyncBarrier.invoke(queue));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    void removeSyncBarrier(){
        if(mTokens==null || mTokens.size()==0){
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            try {
                MessageQueue queue = mHandler.getLooper().getQueue();
                Method removeSyncBarrier = null;
                removeSyncBarrier = queue.getClass().getDeclaredMethod("removeSyncBarrier",int.class);
                removeSyncBarrier.setAccessible(true);
                int token = mTokens.get(mTokens.size()-1);
                removeSyncBarrier.invoke(queue, token);
                mTokens.remove(mTokens.size()-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
