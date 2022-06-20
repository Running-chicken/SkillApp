package com.cc.module.test.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cc.library.base.util.Utils;
import com.cc.module.test.R;
import com.cc.module.test.adapter.RvAdapter;
import com.cc.module.test.databinding.TestLvBinding;
import com.cc.module.test.entity.Pic;
import com.cc.module.test.view.MyItemDecoView;

import java.util.ArrayList;
import java.util.List;

public class MyRvActivity extends AppCompatActivity {

    TestLvBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_my_lv);
        List<Pic> list = new ArrayList<>();

        for(int i=0;i<100;i++){
            list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png",""));
        }


        RvAdapter adapter = new RvAdapter(this,list);
        adapter.setInterface(new RvAdapter.MyRvInterface() {
            @Override
            public void onClick(View view, int position) {
                Utils.toast(MyRvActivity.this,list.get(position).url);
            }
        });

        mBinding.rvTest.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvTest.addItemDecoration(new MyItemDecoView(this));
        mBinding.rvTest.setAdapter(adapter);

        mBinding.rvTest.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(MyRvActivity.this).resumeRequests();
                }else{
                    Glide.with(MyRvActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
}