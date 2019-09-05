package com.cc.skillapp.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.MyRvAdapter;
import com.cc.skillapp.entity.AllDataEntity;
import com.cc.skillapp.entity.Query;
import com.cc.skillapp.utils.XmlParserManager;
import com.cc.skillapp.view.SpaceItemDecoratiion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRv;
    private List<AllDataEntity> mList;
    private MyRvAdapter myRvAdapter;
    //下拉刷新控件
    private PtrClassicFrameLayout toRefreshLayout;
    /**页码*/
    private int pageIndex;
    private int pageSize;
    /**瀑布流manager*/
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    /**是否正在加载*/
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initRefreshView();


        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //初始化瀑布流manager
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE); //防止瀑布流上滑时item变换位置
        mList = new ArrayList<>();
        myRvAdapter = new MyRvAdapter(mContext,mList);
        mRv.addItemDecoration(new SpaceItemDecoratiion(50));
        mRv.setLayoutManager(staggeredGridLayoutManager);
        mRv.setAdapter(myRvAdapter);
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

                    if(!isLoading && (firstVisibleItem+visibleItemCount)>=totalItemCount){
                        isLoading =true;
                        pageIndex++;
                        getLiveData();
//                        Toast.makeText(mContext, "到底啦", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void initView() {
        mRv = findViewById(R.id.rv);
    }

    private void initData() {
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isLoading =false;

                if(response.isSuccessful()){
                    String xmlStr = response.body().string();
                    Log.i("ccan","resultStr:"+xmlStr);
                    Query<AllDataEntity> result = XmlParserManager.getQuery(xmlStr,AllDataEntity.class,"hit",AllDataEntity.class,"hits");
                    mList.addAll(result.getList());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toRefreshLayout.refreshComplete();
                            myRvAdapter.notifyDataSetChanged();
                        }
                    });



                }
            }
        });

    }

    private void initRefreshView() {
        toRefreshLayout = findViewById(R.id.pull_to_refresh);
        toRefreshLayout.setLastUpdateTimeRelateObject(this);
        toRefreshLayout.disableWhenHorizontalMove(true);
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
