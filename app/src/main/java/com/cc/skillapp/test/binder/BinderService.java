package com.cc.skillapp.test.binder;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cc.skillapp.utils.Utils;

public class BinderService extends Service {


    MyBinder mStub = new MyBinder() {
        @Override
        public int add(int arg0, int arg1) {
            return arg0+arg1;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Utils.log("onBind");
        return mStub;
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
