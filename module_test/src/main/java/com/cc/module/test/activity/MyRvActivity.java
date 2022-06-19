package com.cc.module.test.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png","ccccc"));
        list.add(new Pic("","ccccc"));
        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png",""));
        list.add(new Pic("http://imgwcs3.soufunimg.com/news/2020_07/21/d15b3498-67d5-407c-ad2f-19885e007209.png",""));
        list.add(new Pic("","ccccc"));
        list.add(new Pic("","ccccc"));


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


    }
}