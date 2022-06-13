package com.cc.skillapp.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cc.library.base.util.Utils;

public class DongTaiReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.log(getClass(),"动态Receiver接收到："+intent.getStringExtra("params"));
    }
}
