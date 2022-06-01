package com.cc.skillapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityOkHttpBinding;
import com.cc.library.base.netconfig.SSLFactory;
import com.cc.library.base.netconfig.TokenInterceptor;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        mBinding.tvTestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url("http://www.baidu.com").build();
                        Response response = null;
                        try {
                            response = okHttpClient.newCall(request).execute();
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
                                .append(response.body());
                        Message message = new Message();
                        message.obj = stringBuilder.toString();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
                });

            }
        });
    }

}
