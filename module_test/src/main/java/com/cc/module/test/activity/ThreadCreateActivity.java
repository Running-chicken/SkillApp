package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.SystemClock;

import com.cc.library.base.util.Utils;
import com.cc.module.test.R;
import com.cc.module.test.databinding.CreateThreadBinding;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_thread_create);


        CreateThreadBinding binding = DataBindingUtil.setContentView(this,R.layout.test_activity_thread_create);
        binding.tvThreadExtend.setOnClickListener(view -> {
            new MyThread().start();
        });

        binding.tvThreadRun.setOnClickListener(view -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Utils.log("this is runnable");
                }
            }).start();
        });

        binding.tvThreadCallable.setOnClickListener(view -> {
            MyCallable callable = new MyCallable();
            FutureTask<String> futureTask = new FutureTask<>(callable);
            new Thread(futureTask).start();
            try {
                Utils.log(futureTask.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    public static class MyThread extends Thread{

        @Override
        public void run() {
            super.run();
            Utils.log("this is myThread");
        }
    }

    public static class MyCallable implements Callable<String> {


        @Override
        public String call() throws Exception {
            Utils.log("开始："+System.currentTimeMillis());
            SystemClock.sleep(2000);
            Utils.log("结束："+System.currentTimeMillis());
            return "我是你大爷";
        }
    }
}