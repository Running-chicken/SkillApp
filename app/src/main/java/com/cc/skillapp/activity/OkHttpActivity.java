package com.cc.skillapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.library.base.netconfig.CacheInterceptor;
import com.cc.library.base.netconfig.SSLFactory;
import com.cc.library.base.netconfig.TokenInterceptor;
import com.cc.library.base.util.Utils;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityOkHttpBinding;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    ActivityOkHttpBinding mBinding;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what==1) mBinding.tvOkhttpResult.setText((String) message.obj);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_ok_http);
        registerListener();
    }

    private void registerListener() {
        File file = new File(getExternalCacheDir(),"okhttpcache");
        mBinding.tvTestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.addNetworkInterceptor(new CacheInterceptor());
                        builder.cache(new Cache(new File(getExternalCacheDir(),"okhttp"),10*1024*1024));
                        OkHttpClient okHttpClient = builder.build();

                        CacheControl cc = new CacheControl.Builder()
                                .noCache() //不使用缓存,但是保存缓存数据
                                .noStore()//不使用缓存，也不保存缓存数据
                                .onlyIfCached()//只使用缓存
                                .build();


                        Request request = new Request.Builder().url("https://publicobject.com/helloworld.txt").build();
                        Response response = null;
                        try {
                            response = okHttpClient.newCall(request).execute();
                            if(response.isSuccessful()){
                                StringBuilder stringBuilder = new StringBuilder();
                                response.body().string();
//                                stringBuilder.append("code:").append(response.code()).append(" body:").append(response.body().string())
                                stringBuilder.append(" cache:"+response.cacheResponse())
                                .append("\nnet:"+ response.networkResponse());
                                Utils.log(stringBuilder.toString());
                                Message message = new Message();
                                message.obj = stringBuilder.toString();
                                message.what = 1;
                                mHandler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        mBinding.tvTestGetAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder().url("http://www.baidu.com").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("code:")
                                    .append(response.code())
                                    .append(" body:")
                                    .append(response.body());
                            Message message = new Message();
                            message.obj = stringBuilder.toString();
                            message.what = 1;
                            mHandler.sendMessage(message);
                        }
                    }
                });
            }
        });


        mBinding.tvTestPostAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                Map<String,String> map = new HashMap<>();
                map.put("cityCode","110100");
                map.put("month","2018-10");
                map.put("ownerId","standard");
                map.put("cityName","北京");
                String json  = new Gson().toJson(map);

                formBody.add("json",json);
                formBody.add("messagename","tdy_GetCalendarIndexData");
                formBody.add("wirelesscode","966214B63AD14448D4252C6621C49408");
                Request request = new Request.Builder()
                        .url("http://124.251.47.220:8021/land/agentservice.jsp?")
                        .post(formBody.build())
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("code:")
                                .append(response.code())
                                .append(" body:")
                                .append(response.body());
                        Message message = new Message();
                        message.obj = stringBuilder.toString();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
                });
            }
        });

        mBinding.tvTestPostJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //配置拦截机
                builder.addInterceptor(new TokenInterceptor());
                // 配置ssl
                SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory();
                builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

                //初始化okhttp
                OkHttpClient okHttpClient = builder.build();


                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                Map<String,String> map = new HashMap<>();
                map.put("platform","1");
                map.put("parentId","0");
                String jsonStr = new Gson().toJson(map);
                RequestBody requestBody = RequestBody.create(JSON,jsonStr);
                Request request= new Request.Builder()
                        .url("https://api-beta.yjzf.com/yjyz.web.api/v1/menuIcon")
                        .post(requestBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("code:")
                                .append(response.code())
                                .append(" body:")
                                .append(response.body().string());
                        Message message = new Message();
                        message.obj = stringBuilder.toString();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
                });

            }
        });

        String fileName = getExternalFilesDir(null).getAbsolutePath()+"/haha.png";

        mBinding.tvDownload.setOnClickListener(view -> {
            String url = "http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png";
            Request request = new Request.Builder().url(url).build();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //请求body转为字节流
                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(new File(fileName));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len=inputStream.read(bytes)) !=- 1){
                        fileOutputStream.write(bytes,0,len);
                    }
                    fileOutputStream.flush();

                    Utils.log(getClass(),"下载成功");

                }
            });




        });
    }

}
