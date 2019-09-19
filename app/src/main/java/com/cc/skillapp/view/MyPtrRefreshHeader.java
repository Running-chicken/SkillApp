package com.cc.skillapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cc.skillapp.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * 自定义header
 */

public class MyPtrRefreshHeader extends FrameLayout implements PtrUIHandler {

    private TextView tvRefresh;

    public MyPtrRefreshHeader(Context context) {
        super(context);
        init();
    }


    private void init() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_ptrrefresh_header,this);
        tvRefresh = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        tvRefresh.setText("正在更新…");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        tvRefresh.setText("更新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvRefresh.setText("下拉刷新...");
            }
        } else if (currentPos > mOffsetToRefresh) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvRefresh.setText("松开刷新...");
            }
        }

    }

}
