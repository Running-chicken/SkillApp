package com.cc.skillapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.LiveAdapter;
import com.cc.skillapp.entity.AllDataEntity;
import com.cc.skillapp.entity.Query;
import com.cc.skillapp.manager.MyLinearLayoutManager;
import com.cc.skillapp.utils.LoadMoreWrapper;
import com.cc.skillapp.utils.XmlParserManager;
import com.cc.skillapp.view.SpaceItemDecoratiion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SwipeRefreshLayoutActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRv;
    private List<AllDataEntity> mList;
    private LoadMoreWrapper loadMoreWrapper;
    /**页码*/
    private int pageIndex;
    private int pageSize;
    private MyLinearLayoutManager linearLayoutManager;
    /**是否正在加载*/
    private boolean isLoading;
    private int totalCount;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                pageIndex++;
                getLiveData();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_layout);
        initView();
        linearLayoutManager = new MyLinearLayoutManager(mContext); //初始化瀑布流manager
        mList = new ArrayList<>();
        LiveAdapter liveAdapter = new LiveAdapter(mList);
        loadMoreWrapper = new LoadMoreWrapper(liveAdapter);
        mRv.addItemDecoration(new SpaceItemDecoratiion(50));
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(loadMoreWrapper);
        initData();
        registerListener();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.my_swipe);
        mRv = findViewById(R.id.rv_swipe);
    }

    private void registerListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                pageIndex = 1;
                getLiveData();
            }
        });

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int visibleItemCount = linearLayoutManager.getChildCount(); //得到显示屏幕内的item
                    int totalItemCount = linearLayoutManager.getItemCount(); //得到item总数
                    int firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    if(!isLoading && (firstVisibleItem+visibleItemCount)>=totalItemCount && mList.size()<totalCount){
                        isLoading =true;
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                        mHandler.sendEmptyMessageDelayed(100,200);
                    }

                }
            }
        });

    }


    private void initData() {
        totalCount = 0;
        isLoading = false;
        getLiveData();
    }

    private void getLiveData(){
        //http://search2.fang.com//video/api/v1/videoxml?page=1&pagetype=live&vc=1dfe402f39489e084aa6633aea442f6f&city=北京
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://search2.fang.com//video/api/v1/videoxml?page="+pageIndex+"&pagetype=live&vc=1dfe402f39489e084aa6633aea442f6f&city=北京";
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                isLoading = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        mList.clear();
                        pageIndex = 1;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isLoading =false;

                if(response.isSuccessful()){
                    String xmlStr = response.body().string();
                    Log.i("ccan","resultStr:"+xmlStr);
                    Query<AllDataEntity> result = XmlParserManager.getQuery(xmlStr,AllDataEntity.class,"hit",AllDataEntity.class,"hits");
                    AllDataEntity allDataEntity = (AllDataEntity) result.getBean();
                    totalCount = Integer.parseInt(allDataEntity.total);
                    mList.addAll(result.getList());

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        if(mList.size()>=totalCount){
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                        }else{
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                        }
                    }
                });
            }
        });

    }


}
