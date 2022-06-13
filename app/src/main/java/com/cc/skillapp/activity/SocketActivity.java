package com.cc.skillapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cc.library.base.util.SocketUtil;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivitySocketBinding;

public class SocketActivity extends AppCompatActivity {

    StringBuilder stringBuilder;
    private String result;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what==100){


                stringBuilder.append(result+"\n");
                mBinding.tvShow.setText(stringBuilder.toString());
            }


            return false;
        }
    });

    ActivitySocketBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySocketBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        stringBuilder = new StringBuilder();

        mBinding.tvSendMsg.setOnClickListener(view -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    result = new SocketUtil(
                            "192.168.1.4").sentMsg(mBinding.etInput.getText().toString());
                    handler.sendEmptyMessage(100);
                }
            }).start();


        });

    }
}