package com.cc.skillapp.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.cc.skillapp.R;
import com.cc.library.base.util.Utils;

public class MyForegroundService extends Service {

    public String channelId = "myChannelId";
    public String channelName= "myChannelName";
    public String describe= "myChannelDescribe";

    public MyBinder myBinder = new MyBinder();

    public MyForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return myBinder;
    }

    class MyBinder extends Binder{

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log(getClass(),"onCreate");

//        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,0,new Intent(),PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        builder.setSmallIcon(R.mipmap.home_product_ad);
        builder.setTicker("通知到来");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("通知标题");
        builder.setContentText("通知内容");
        builder.setContentIntent(pendingIntent);

        startForeground(1,builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        Utils.log(getClass(),"onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log(getClass(),"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}