package com.cc.skillapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cc.skillapp.entity.ActivityManagerNative;

public class ActivityManagerService extends Service {
    public ActivityManagerService() {
    }

    ActivityManagerNative stub = new ActivityManagerNative() {
        @Override
        public int startActivity(Intent intent) {
            return 3191;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


}