package com.cc.skillapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.cc.skillapp.utils.Utils;

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
    public void onDestroy() {
        super.onDestroy();
        Utils.log(getClass(),"intentService onDestroy");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Utils.log(getClass(),"intentService onHandleIntent");
        if (intent != null) {
            String params = intent.getStringExtra("params");
            if(params.equals("wiiwafefeafefeaooji")){
                for(String str : params.split("a")){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.log(getClass(),"执行:"+str);
                }
            }else if(params.equals("this.is.my.test.b")){
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


}