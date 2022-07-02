package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.ImageViewBindingAdapter;

import android.os.Bundle;
import android.os.SystemClock;

import com.bumptech.glide.util.Util;
import com.cc.library.base.util.Utils;
import com.cc.module.test.R;
import com.cc.module.test.databinding.ThreadPoolBinding;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends AppCompatActivity {

    ScheduledFuture scheduledFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_thread_pool);


        ThreadPoolBinding binding = DataBindingUtil.setContentView(this,R.layout.test_activity_thread_pool);
        binding.tvFix.setOnClickListener(view -> {
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            MyRunnable r = new MyRunnable();
            Callable<String> callable = new Callable() {
                @Override
                public String call() throws Exception {
                    Utils.log("callable execute");
                    SystemClock.sleep(2000);
                    return "真真真可爱";
                }
            };
            FutureTask<String> futureTask = new FutureTask<>(callable);
            executorService.submit(r);
            executorService.submit(futureTask);
            try {
                Utils.log(futureTask.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });

        binding.tvSingle.setOnClickListener(view -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
        });

        binding.tvCacehd.setOnClickListener(view -> {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
        });

        binding.tvScheduleOne.setOnClickListener(view -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
            executorService.schedule(new MyRunnable(),2, TimeUnit.SECONDS);
        });



        binding.tvSchedule.setOnClickListener(view -> {
            MyRunnable r = new MyRunnable();
            scheduledFuture = Executors.newScheduledThreadPool(5).scheduleAtFixedRate(r,2,2, TimeUnit.SECONDS);
        });

        binding.tvScheduleClose.setOnClickListener(view -> {
            scheduledFuture.cancel(true);
        });
    }




    public static class MyRunnable implements Runnable{

        @Override
        public void run() {
            Utils.log(getClass(),"fix pool"+ System.currentTimeMillis());
        }
    }
}