package com.cc.skillapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cc.library.base.entity.TestLisTEntity;
import com.cc.library.base.entity.TestLisTEntity.MenuIcon;
import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.MenuIconAdapter;
import com.cc.skillapp.manager.FullyLinearLayoutManager;
import com.cc.skillapp.utils.LoadMoreWrapper;
import com.cc.library.base.netconfig.SSLFactory;
import com.cc.library.base.netconfig.TokenInterceptor;
import com.cc.skillapp.view.MaxHeightRecyclerView;
import com.cc.skillapp.view.MyScrollView;
import com.cc.skillapp.view.SpaceItemDecoratiion;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RvSuspensionActivity extends BaseActivity implements MyScrollView.OnScrollListener {


    MaxHeightRecyclerView mRv;
    LinearLayout llExternal,llXf;
    TextView tvXf;

    private List<MenuIcon> mList;
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
        MenuIconAdapter liveAdapter = new MenuIconAdapter(mList);
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
                .url(url).post(requestBody)
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
