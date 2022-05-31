package com.cc.skillapp.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


import com.cc.skillapp.utils.Utils;

public class AidlService extends Service {

    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub(){


        @Override
        public void helloActivity(String params) throws RemoteException {
           Utils.log(getClass(),params);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Utils.log("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.log("onDestroy");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log("onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
