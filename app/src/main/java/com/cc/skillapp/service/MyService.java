package com.cc.skillapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.cc.skillapp.R;
import com.cc.skillapp.activity.ServiceActivity;
import com.cc.skillapp.utils.Utils;

public class MyService extends Service {
    MyBinder myBinder = new MyBinder();

    private NotificationManager manager;
    private int notification_id = 1;
    public String channelId = "myChannelId";
    public String channelName= "myChannelName";
    public String describe= "myChannelDescribe";

    @Override
    public IBinder onBind(Intent intent) {
        Utils.log(getClass(),"onBind");
        return myBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.log("service onCreate");
        NotificationChannel notificationChannel;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int important = NotificationManager.IMPORTANCE_HIGH;
            notificationChannel = new NotificationChannel(channelId,channelName,important);
            notificationChannel.setDescription(describe);
            manager.createNotificationChannel(notificationChannel);
        }

        showNotification();

    }

    private void showNotification() {

        CharSequence charSequence = "this a ticker";
        PendingIntent intent = PendingIntent.getActivity(this,0,
                new Intent(this, ServiceActivity.class),0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId);
        notificationBuilder.setSmallIcon(R.mipmap.home_product_ad)
                .setTicker(charSequence)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("this is content title")
                .setContentText("this is content text")
                .setContentIntent(intent)
                .setPriority(NotificationCompat.PRIORITY_MAX).setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        manager.notify(notification_id,notificationBuilder.build());
        Utils.log(getClass(),"通知栏已出");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.log("service onDestroy");
        manager.cancel(notification_id);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.log("service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.log("service onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{

        public MyService getService(){
            return MyService.this;
        }

    }
}