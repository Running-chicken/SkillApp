package com.cc.skillapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.LiveAdapter;
import com.cc.skillapp.entity.AllDataEntity;
import com.cc.skillapp.entity.Query;
import com.cc.skillapp.manager.FullyLinearLayoutManager;
import com.cc.skillapp.manager.MyLinearLayoutManager;
import com.cc.skillapp.utils.LoadMoreWrapper;
import com.cc.skillapp.utils.XmlParserManager;
import com.cc.skillapp.view.MaxHeightRecyclerView;
import com.cc.skillapp.view.MyScrollView;
import com.cc.skillapp.view.SpaceItemDecoratiion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RvSuspensionActivity extends BaseActivity implements MyScrollView.OnScrollListener {


    MaxHeightRecyclerView mRv;
    LinearLayout llExternal,llXf;
    TextView tvXf;

    private List<AllDataEntity> mList;
    private LoadMoreWrapper loadMoreWrapper;
    /**页码*/
    private int pageIndex;
    private int pageSize;
    private FullyLinearLayoutManager linearLayoutManager;
    private int totalCount;

    private MyScrollView myScrollView;
    private int heightPixels;
    private boolean isAddMore = false;
    /**
     * 当前滑动的Y值
     */
    private int mScrollY = 0;
    private int mHeightToTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_suspension);

        initView();

        linearLayoutManager = new FullyLinearLayoutManager(mContext); //初始化瀑布流manager
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
        mRv = findViewById(R.id.rv_xf);
        llExternal = findViewById(R.id.ll_external);
        llXf = findViewById(R.id.ll_xf);
        tvXf = findViewById(R.id.tv_xf);
        myScrollView = findViewById(R.id.sv_xf);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        heightPixels = outMetrics.heightPixels;
        mRv.setMaxHeight(heightPixels - dp2px(mContext,50));
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int itemCount, lastPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //停止滚动
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //滚动 且手指触碰屏幕
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //由于惯性滚动
                        break;
                }

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    itemCount = linearLayoutManager.getItemCount();
                    lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if(lastPosition == itemCount-1){
                        isAddMore = true;
                        loadMoreWrapper.setLoadState(1);
                    }

                }


            }

        });

        mRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(isAddMore && myScrollView!=null){
                    isAddMore = false;
                    pageIndex++;
                    getLiveData();

                }
            }
        });

    }

    public static int dp2px(Context context, float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    private void initData() {
        pageIndex = 1;
        totalCount = 0;
        getLiveData();
        setChangeTabScroAndRecycListener(mScrollY);
    }


    private void registerListener() {

        myScrollView.setOnScrollListener(this);
    }

    private void setScroAndRecycListener(int scrollY) {
        if(llXf!=null){
            mHeightToTop = llXf.getTop();
        }
        if(scrollY>mHeightToTop){ //使用rv
            myScrollView.scrollTo(0,mHeightToTop);
            mRv.setHasFixedSize(false);
            mRv.setNestedScrollingEnabled(true);
            linearLayoutManager.setScrollEnabled(true);

            if(tvXf.getParent()!=llExternal){
                llXf.removeView(tvXf);
                llExternal.addView(tvXf);
            }
        }else{
            mRv.setHasFixedSize(true);
            mRv.setNestedScrollingEnabled(false);
            linearLayoutManager.setScrollEnabled(false);

            myScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            if(myScrollView.getChildAt(0).getMeasuredHeight()<=myScrollView.getHeight()+myScrollView.getScrollY()){
                                if(heightPixels>mRv.getMeasuredHeight()){
                                    isAddMore = true;
                                    getLiveData();
                                }

                            }
                            break;
                    }
                    return false;
                }
            });

            if(tvXf.getParent()!=llXf){
                llExternal.removeView(tvXf);
                llXf.addView(tvXf);
            }
        }
    }

    private void setChangeTabScroAndRecycListener(int scrollY) {
        if (llXf != null) {
            mHeightToTop = llXf.getTop();
        }
        if (scrollY > mHeightToTop) {
            mRv.setHasFixedSize(false);
            mRv.setNestedScrollingEnabled(true);
            linearLayoutManager.setScrollEnabled(true);

            myScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            if (tvXf.getParent() != llExternal) {
                llXf.removeView(tvXf);
                llExternal.addView(tvXf);
            }
        } else {
            mRv.setHasFixedSize(true);
            mRv.setNestedScrollingEnabled(false);
            linearLayoutManager.setScrollEnabled(false);

            myScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            if (tvXf.getParent() != llXf) {
                llExternal.removeView(tvXf);
                llXf.addView(tvXf);
            }
        }
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        pageIndex = 1;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

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


    @Override
    public void onScroll(int scrollY, int oldY) {
        mScrollY = scrollY;
        setScroAndRecycListener(scrollY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (llXf != null) {
                mHeightToTop = llXf.getTop();
            }
        }


    }
}
