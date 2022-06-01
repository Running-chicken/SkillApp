package com.cc.module.test.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cc.library.base.entity.TestLisTEntity;
import com.cc.library.base.netconfig.RetrofitManager;
import com.cc.library.base.util.LogUtils;
import com.cc.library.base.util.Utils;
import com.cc.module.test.api.TestRxJavaApi;
import com.cc.module.test.databinding.TestActivityRxjavaBinding;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private TestActivityRxjavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = TestActivityRxjavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getRetrofit();

    }


    private void getRetrofit(){

        Map<String,Object> params = new HashMap<>();
        params.put("platform",1);
        params.put("parentId",0);

        RetrofitManager.create(TestRxJavaApi.class)
                .getMenuIcon(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestLisTEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TestLisTEntity testLisTEntity) {
                        Utils.log("结果是："+testLisTEntity.data.size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Utils.log("请求成功");
                    }
                });


    }

    private void testRxJava(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                LogUtils.getInstance().info(Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                LogUtils.getInstance().info(Thread.currentThread().getName());
                LogUtils.getInstance().info("accept"+integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.getInstance().info("Thread"+Thread.currentThread().getName());
                        LogUtils.getInstance().info("onNext"+integer);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(consumer);
    }



}



