package com.cc.module.test.entity;

import com.cc.library.base.util.LogUtils;
import com.cc.library.base.util.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class RxjavaDemo {


    public static void main(String[] args) {

//        testSubscribeHasObserver();


        testSubscribeHasConsumer();

    }

    public static void testSubscribeHasConsumer(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("emit 1");
                emitter.onNext(1);
                System.out.println("emit 2");
                emitter.onNext(2);
                System.out.println("emit 3");
                emitter.onNext(3);
                System.out.println("emit complete");
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("accept "+integer);
            }
        });
    }


    public static void testSubscribeHasObserver(){
        //创建上游Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                System.out.println("emit 1");
                emitter.onNext(2);
                System.out.println("emit 2");
                emitter.onNext(3);
                System.out.println("emit 3");
                emitter.onComplete();
                System.out.println("emit complete");
                emitter.onNext(4);
                System.out.println("emit 4");
            }
        }).subscribe(new Observer<Integer>() {//连接下游
            Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("onNext"+integer);
                if(integer==2){
                    System.out.println("dispose");
                    mDisposable.dispose();
                    System.out.println("isDisposed:"+mDisposable.isDisposed());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

}
