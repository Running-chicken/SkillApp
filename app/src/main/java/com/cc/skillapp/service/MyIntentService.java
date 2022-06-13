package com.cc.skillapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.cc.library.base.util.Utils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log(getClass(),"intentService onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Utils.log(getClass(),"onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Utils.log(getClass(),"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Utils.log(getClass(),"onBind");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.log(getClass(),"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.log(getClass(),"intentService onDestroy");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Utils.log(getClass(),"intentService onHandleIntent");
        if (intent != null) {
            String params = intent.getStringExtra("params");
            for(String str : params.split("\\.")){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Utils.log(getClass(),"执行:"+str);
            }

        }
    }


}