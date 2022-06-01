package com.cc.module.test.activity;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cc.library.base.entity.Person;
import com.cc.library.base.entity.TestLisTEntity;
import com.cc.library.base.netconfig.RetrofitManager;
import com.cc.library.base.util.DBHelper;
import com.cc.library.base.util.LogUtils;
import com.cc.library.base.util.Utils;
import com.cc.module.test.api.TestRxJavaApi;
import com.cc.module.test.databinding.TestActivityRxjavaBinding;
import com.cc.module.test.entity.LocationEntity;
import com.cc.module.test.viewmodel.RxJavaViewModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private TestActivityRxjavaBinding binding;
    private RxJavaViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = TestActivityRxjavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(RxJavaViewModel.class);
        binding.setViewModel(mViewModel);


//        getRetrofit();
//        getSqlite();
//        testRxJava();
//        testMap();
//        testFlatmap();
        testFlatmapUpdate();
    }

    private void testFlatmapUpdate(){
        Map<String,Object> params = new HashMap<>();
        params.put("latitude","39.915794");
        params.put("longitude","116.40289");
        RetrofitManager.create(TestRxJavaApi.class)
                .getLocationInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LocationEntity>() {
                    @Override
                    public void accept(LocationEntity locationEntity) throws Exception {
                        Utils.log("先收割一波，取到城市名字："+locationEntity.data.cityName);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<LocationEntity, ObservableSource<TestLisTEntity>>() {
                    @Override
                    public ObservableSource<TestLisTEntity> apply(@NonNull LocationEntity locationEntity) throws Exception {
                        Utils.log("继续收割，取到城市code："+locationEntity.data.cityCode);
                        Map<String,Object> iconParams = new HashMap<>();
                        iconParams.put("platform",1);
                        iconParams.put("parentId",0);
                        return RetrofitManager.create(TestRxJavaApi.class).getMenuIcon(iconParams);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestLisTEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TestLisTEntity testLisTEntity) {
                        mViewModel.iconItems.addAll(testLisTEntity.data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void testFlatmap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(8);
                emitter.onNext(5);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for(int i=0;i<2;i++){
                    list.add(integer*i+" is new value");
                }

                return Observable.fromIterable(list).delay(3, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Utils.log("输出是："+o);
            }
        });
    }

    /**
     * map函数 ：将Observable传值转成其他类型传给Observer
     */
    private void testMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1993);
            }
        }).map(new Function<Integer, String>() {

            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "this is" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Utils.log("输出是："+s);
            }
        });
    }

    /**
     * 线程调度 在io线程处理耗时操作
     */
    private void getSqlite(){
        Observable.create(new ObservableOnSubscribe<List<Person>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Person>> emitter) throws Exception {
                DBHelper dbHelper = DBHelper.getInstance(RxJavaActivity.this);
                Cursor cursor = dbHelper.getReadableDatabase().query("person",null,null,null,null,null,null);
                List<Person> result = new ArrayList<>();
                while (cursor.moveToNext()){
                    Person person = new Person(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
                    result.add(person);
                }
                emitter.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Person>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Person> person) {
                        if(person!=null && person.size()>0){
                            mViewModel.items.addAll(person);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Utils.log("数据库加载完成");
                    }
                });
    }


    /**
     * Retrofit+rxJava  线程调度
     */
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

    /**
     * consumer函数 只接收onNext
     */
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


