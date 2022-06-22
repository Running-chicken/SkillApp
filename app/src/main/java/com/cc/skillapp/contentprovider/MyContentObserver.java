package com.cc.skillapp.contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class MyContentObserver extends ContentObserver {

    Handler handler;
    Context context;


    public MyContentObserver(Handler handler,Context context) {
        super(handler);
        this.handler = handler;
        this.context =context;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.skill.my.provider/person"),null,"id=?",new String[]{"1"},null,null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                handler.obtainMessage(100,cursor.getString(1)).sendToTarget();
//                Utils.log(getClass(),"id="+cursor.getInt(0) + " name="+cursor.getString(1)+" age="+cursor.getInt(2));
            }
        }

    }
}
