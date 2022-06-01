package com.cc.skillapp.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;

import com.cc.library.base.util.Utils;

public class DownTask extends HandlerThread {

    private Handler mHandler;

    public DownTask(String name) {
        super(name);
    }


    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        Utils.log(getClass(),"onLooperPrepared");
    }

    public void setHandler(Handler handler){
        this.mHandler = handler;
    }

    public void startTask(){
        mHandler.sendEmptyMessage(0);
        Utils.log(getClass(),"task start");
        SystemClock.sleep(3000);
        Utils.log(getClass(),"task end");
        mHandler.sendEmptyMessage(1);
    }
}
