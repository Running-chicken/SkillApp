package com.cc.skillapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class InfiniteShufflingViewPager extends ViewPager {

    private int startX;
    private int startY;
    private boolean isCanScroll = true;


    public InfiniteShufflingViewPager(@NonNull Context context) {
        super(context);
    }

    public InfiniteShufflingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dX = endX - startX;
                int dY = endY - startY;
                if(Math.abs(dX) > Math.abs(dY)){
                    if(getCurrentItem() == 0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        int count = getAdapter().getCount();
                        if(getCurrentItem() == count-1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        if(isCanScroll){
            super.scrollTo(x, y);
        }
    }
}
