package com.cc.skillapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cc.library.base.entity.TestLisTEntity;
import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityTestBinding;
import com.cc.library.base.netconfig.SSLFactory;
import com.cc.library.base.netconfig.TokenInterceptor;
import com.cc.skillapp.viewmodel.TestViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TestActivity extends AppCompatActivity {

    ActivityTestBinding mBinding;
    TestViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test);
        mViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        mBinding.setViewModel(mViewModel);


        initData();

        mBinding.vMy.setOnClickListener(view -> startActivity(
                new Intent(TestActivity.this, TestLifeAActivity.class).putExtra("param","singleTask")));
    }

    private void initData() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new TokenInterceptor());
        SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory();
        clientBuilder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);

        OkHttpClient okHttpClient = clientBuilder.build();

        Map<String,String> params = new HashMap<>();
        params.put("platform","1");
        params.put("parentId","0");

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType,new Gson().toJson(params));

        Request request = new Request.Builder().url("https://api-beta.yjzf.com/yjyz.web.api/v1/menuIcon").post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonStr = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TestLisTEntity testLiSTEntity = null;
                            try {
                                testLiSTEntity = new Gson().fromJson(jsonStr, TestLisTEntity.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                            if(testLiSTEntity!=null){
                                mViewModel.items.addAll(testLiSTEntity.data);
                            }
                        }
                    });

                }
            }
        });
    }

}