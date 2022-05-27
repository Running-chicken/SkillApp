package com.cc.skillapp.test.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.LruCache;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.MyVPAdapter;
import com.cc.skillapp.databinding.ActivityEventBinding;
import com.cc.skillapp.test.view.TestListView;
import com.cc.skillapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {




    String[] strArray = new String[]{
            "this is item","this is item","this is item","this is item","this is item","this is item","this is item","this is item",
            "this is item","this is item","this is item","this is item","this is item","this is item","this is item","this is item",
            "this is item","this is item","this is item","this is item","this is item","this is item","this is item","this is item",
            "this is item","this is item","this is item","this is item","this is item","this is item","this is item","this is item",
            "this is item","this is item","this is item","this is item","this is item","this is item","this is item","this is item"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEventBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_event);


        List<View> list = new ArrayList<>();
        //外部拦截
//        for(int i=0;i<3;i++){
//            Button textView = new Button(this);
//            textView.setText("this is item"+i);
//            list.add(textView);
//        }

//        for(int i=0;i<3;i++){
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strArray);
//            ListView listView = new ListView(this);
//            listView.setAdapter(adapter);
//            list.add(listView);
//        }

        //内部拦截法
         for(int i=0;i<3;i++){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strArray);
            TestListView listView = new TestListView(this);
            listView.setAdapter(adapter);
            list.add(listView);
        }


        MyVPAdapter myVPAdapter = new MyVPAdapter(list);
        mBinding.vpEvent.setAdapter(myVPAdapter);


    }
}