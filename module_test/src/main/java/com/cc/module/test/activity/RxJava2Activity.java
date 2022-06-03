package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cc.library.base.util.Utils;
import com.cc.module.test.databinding.TestActivityRxjava2Binding;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Activity extends AppCompatActivity {

    TestActivityRxjava2Binding mBinding;
    Subscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = TestActivityRxjava2Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvRxjava2.setOnClickListener(view -> {
            request();
        });

//        testFlowable();

        testMaybe();
    }


    private void testMaybe(){
        Maybe.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Utils.log("输出："+integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }


    //动态请求5个
    private void request(){
        mSubscription.request(5);
    }

    private void testFlowable(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {

                for(int i =0;i<30;i++){
                    emitter.onNext(i);
                    Utils.log("下游处理数量："+emitter.requested());
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Utils.log("输出："+integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        flowable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);

    }

}