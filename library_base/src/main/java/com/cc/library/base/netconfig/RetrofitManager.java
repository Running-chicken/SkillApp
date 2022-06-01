package com.cc.library.base.netconfig;

import com.cc.library.base.netconfig.converter.GsonConverterFactory;
import com.cc.library.base.netconfig.converter.NoBodyConverterFactory;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Retrofit封装类
 */

public class RetrofitManager {


    //单例
    private static RetrofitManager mInstance;
    //Retrofit实例
    private static Retrofit retrofit;

    /**
     * 如果两个线程同时走到 if (mInstance == null) 如果不再判断一次，就会生成两个对象
     * @return
     */
    public static RetrofitManager getInstance(){
        if(mInstance==null){
            synchronized (RetrofitManager.class){
                if(mInstance==null){
                    mInstance = new RetrofitManager();
                }
            }
        }

        return mInstance;
    }


    //初始化
    public void init(String host,boolean canProxy){
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient(canProxy)) //配置okHttp
                .baseUrl(host) //域名  https://api-beta.yjzf.com
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
                .addConverterFactory(NoBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    //获取okHttpClient
    public static OkHttpClient getOkHttpClient(boolean canProxy){
        return getOkHttpBuilder(canProxy).build();
    }

    //配置okHttpClient
    public static OkHttpClient.Builder getOkHttpBuilder(boolean canProxy){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(2,TimeUnit.MINUTES);

        //设置header
        builder.addInterceptor(new TokenInterceptor());

        builder.proxy(Proxy.NO_PROXY);

        //配置ssl协议
        SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);

        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        return builder;
    }

    /**
     * 构建指定的API服务
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> serviceClass){
        return retrofit.create(serviceClass);
    }






}
