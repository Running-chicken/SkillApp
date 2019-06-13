package com.cc.skillapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.R;
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

    private TextView tv_test_get;
    private TextView tv_test_get_async;
    private TextView tv_test_post_async;
    private TextView tv_test_post_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        initView();
        registerListener();
    }

    private void registerListener() {
        tv_test_get.setOnClickListener(new View.OnClickListener() {
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
                                Log.i("cc","code："+response.code());
                                Log.i("cc","message："+response.message());
                                Log.i("cc","body："+response.body().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        tv_test_get_async.setOnClickListener(new View.OnClickListener() {
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
                            Log.i("cc","code："+response.code());
                            Log.i("cc","请求数据成功:"+response.message());
                            Log.i("cc","body："+response.body().string());
                        }
                    }
                });
            }
        });


        tv_test_post_async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("username","zhangsan");
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .post(formBody.build())
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("cc","code："+response.code());
                        Log.i("cc","message:"+response.message());
                        Log.i("cc","body："+response.body().string());
                    }
                });
            }
        });

        tv_test_post_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                Map<String,String> map = new HashMap<>();
                map.put("username","lisi");
                map.put("nickname","李四");
                String jsonStr = new Gson().toJson(map);                RequestBody requestBody = RequestBody.create(JSON,jsonStr);
                Request request= new Request.Builder().url("http://www.baidu.com").post(requestBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("cc","code："+response.code());
                        Log.i("cc","message:"+response.message());
                        Log.i("cc","body："+response.body().string());
                    }
                });

            }
        });
    }

    private void initView() {
        tv_test_get = findViewById(R.id.tv_test_get);
        tv_test_get_async = findViewById(R.id.tv_test_get_async);
        tv_test_post_async = findViewById(R.id.tv_test_post_async);
        tv_test_post_json = findViewById(R.id.tv_test_post_json);
    }
}
