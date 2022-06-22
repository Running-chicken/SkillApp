package com.cc.skillapp.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.library.base.util.Utils;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ContentObserverBinding;

import java.util.Random;

public class ContentObserverActivity extends AppCompatActivity {

    ContentObserverBinding mBinding;
    MyContentObserver observer;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    mBinding.tvShowStatus.setText(String.valueOf(msg.obj));

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_observer);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_content_observer);


        observer = new MyContentObserver(handler,this);
        Uri uri = Uri.parse("content://com.skill.my.provider/person");
        getContentResolver().registerContentObserver(uri,false,observer);

        mBinding.tvStartAir.setOnClickListener(view -> {

        });

        mBinding.tvQuery.setOnClickListener(view -> {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(Uri.parse("content://com.skill.my.provider/person"),null,"id=?",new String[]{"1"},null,null);
            if (cursor != null) {
                while (cursor.moveToNext()){
                    Utils.log("id="+cursor.getInt(0) + " name="+cursor.getString(1)+" age="+cursor.getInt(2));
                }
            }
        });

        mBinding.tvUpdate.setOnClickListener(view -> {
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            Random random = new Random();

            contentValues.put("name","菲菲"+random.nextInt(100));
            contentValues.put("age",random.nextInt(100));
            contentResolver.update(Uri.parse("content://com.skill.my.provider"),contentValues,"id=?",new String[]{"1"});
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}