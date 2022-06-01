package com.cc.skillapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.MenuIconAdapter;
import com.cc.skillapp.entity.TestLisTEntity;
import com.cc.skillapp.entity.TestLisTEntity.MenuIcon;
import com.cc.skillapp.manager.MyStaggeredGridLayoutManager;
import com.cc.skillapp.utils.LoadMoreWrapper;
import com.cc.library.base.netconfig.SSLFactory;
import com.cc.library.base.netconfig.TokenInterceptor;
import com.cc.skillapp.view.MyPtrRefreshHeader;
import com.cc.skillapp.view.SpaceItemDecoratiion;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRv;
    private List<MenuIcon> mList;
    private LoadMoreWrapper loadMoreWrapper;
    //下拉刷新控件
    private PtrClassicFrameLayout toRefreshLayout;
    /**页码*/
    private int pageIndex;
    private int pageSize;
    /**瀑布流manager*/
    private MyStaggeredGridLayoutManager staggeredGridLayoutManager;
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
        setContentView(R.layout.activity_recycler_view);
        initView();
        initRefreshView();


        staggeredGridLayoutManager = new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //初始化瀑布流manager
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE); //防止瀑布流上滑时item变换位置
        mList = new ArrayList<>();
        MenuIconAdapter liveAdapter = new MenuIconAdapter(mList);
        loadMoreWrapper = new LoadMoreWrapper(liveAdapter);
        mRv.addItemDecoration(new SpaceItemDecoratiion(50));
        mRv.setLayoutManager(staggeredGridLayoutManager);
        mRv.setAdapter(loadMoreWrapper);
        initData();

        registerListener();
    }

    private void registerListener() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int visibleItemCount = staggeredGridLayoutManager.getChildCount(); //得到显示屏幕内的item
                    int totalItemCount = staggeredGridLayoutManager.getItemCount(); //得到item总数
                    int firstVisibleItems[] = null;
                    firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    int firstVisibleItem = firstVisibleItems[0];

                    if(!isLoading && (firstVisibleItem+visibleItemCount)>=totalItemCount && mList.size()<totalCount){
                        isLoading =true;
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                        mHandler.sendEmptyMessageDelayed(100,200);
                    }

                }
            }
        });

    }

    private void initView() {
        mRv = findViewById(R.id.rv);
    }

    private void initData() {
        totalCount = 0;
        isLoading = false;
        getLiveData();
    }

    private void getLiveData(){

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new TokenInterceptor());
        SSLFactory.SSLParams sslParams = SSLFactory.getSslSocketFactory();
        clientBuilder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);

        OkHttpClient okHttpClient = clientBuilder.build();

        String url = "https://api-beta.yjzf.com/yjyz.web.api/v1/menuIcon";

        Map<String,String> params = new HashMap<>();
        params.put("platform","1");
        params.put("parentId","0");

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(mediaType,new Gson().toJson(params));

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                isLoading = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toRefreshLayout.refreshComplete();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                isLoading =false;

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
                                mList.addAll(testLiSTEntity.data);
                            }

                            toRefreshLayout.refreshComplete();

                            totalCount = mList.size();
                            if(mList.size()>=totalCount){
                                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                            }else{
                                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                            }
                        }
                    });

                }


            }
        });

    }

    private void initRefreshView() {
        MyPtrRefreshHeader myRefreshHeader = new MyPtrRefreshHeader(mContext);


        toRefreshLayout = findViewById(R.id.pull_to_refresh);
        toRefreshLayout.setLastUpdateTimeRelateObject(this);
        toRefreshLayout.disableWhenHorizontalMove(true);

        toRefreshLayout.setHeaderView(myRefreshHeader);
        toRefreshLayout.addPtrUIHandler(myRefreshHeader);

        toRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame,content,header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });



    }

    private void refresh(){
        mList.clear();
        pageIndex = 1;
        getLiveData();
    }
}
