package com.cc.skillapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;

import com.cc.library.base.util.MyLifecycleObserver;
import com.cc.library.base.util.Utils;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTest2Binding;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TestLifeAActivity extends RxAppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTest2Binding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test2);
        mBinding.tvStart.setOnClickListener(view ->
                {
                    Intent intent = new Intent();
                    intent.setClass(this, TestLifeAActivity.class);
                    startActivity(intent);
                }

        );

        mBinding.tvStart1.setOnClickListener(view -> {
            Intent intent = new Intent(this, TestLifeBActivity.class);
            startActivity(intent);
        });

        if(savedInstanceState!=null){
            Utils.log(getClass(),"onCreate bundle="+savedInstanceState.getString("save"));
        }else {
            Utils.log(getClass(),"onCreate");
        }




        mBinding.tvStartData.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setData(Uri.parse("cc://can3191:8888/niu"));
            startActivity(intent);
        });

        mBinding.tvLifeLifecycle.setOnClickListener(view -> {
            testRxJavaTime();
        });

        mBinding.tvLifeLifecycle2.setOnClickListener(view -> {
            Observable.interval(0,2,TimeUnit.SECONDS).doOnDispose(new Action() {
                @Override
                public void run() throws Exception {
                    Utils.log("截断");
                }
            }).compose(new ObservableTransformer<Long, Long>() {

                @io.reactivex.annotations.NonNull
                @Override
                public ObservableSource<Long> apply(@io.reactivex.annotations.NonNull Observable<Long> upstream) {
                    return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                }
            }).compose(AndroidLifecycle.createLifecycleProvider(this).bindUntilEvent(Lifecycle.Event.ON_STOP))
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Utils.log("输出："+aLong);
                        }
                    });
        });


        Lifecycle lifecycle = getLifecycle();
        lifecycle.addObserver(new MyLifecycleObserver());
    }




    private void testRxJavaTime(){
        Observable.interval(0,1,TimeUnit.SECONDS).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                Utils.log("this is dispose");
            }
        }).compose(new ObservableTransformer<Long, Long>() {

            @io.reactivex.annotations.NonNull
            @Override
            public ObservableSource<Long> apply(@io.reactivex.annotations.NonNull Observable<Long> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        })
                .compose(bindUntilEvent(ActivityEvent.STOP))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Utils.log("输出："+aLong);
                    }
                });
    }

    @Override
    protected void onResume() {
        Utils.log(getClass(),"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Utils.log(getClass(),"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Utils.log(getClass(),"onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Utils.log(getClass(),"onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Utils.log(getClass(),"onRestart");
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Utils.log(getClass(),"onRestoreInstanceState bundle="+savedInstanceState.getString("save"));
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Utils.log(getClass(),"onSaveInstanceState");
        outState.putString("save","had save bundle");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Utils.log(getClass(),"onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Utils.log(getClass(),"onNewIntent");
        super.onNewIntent(intent);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Utils.log(getClass(),"onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}