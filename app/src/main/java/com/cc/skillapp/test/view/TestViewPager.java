package com.cc.skillapp.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class TestViewPager extends ViewPager {
    public TestViewPager(@NonNull Context context) {
        super(context);
    }

    public TestViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastXIntercept;
    private int mLastYIntercept;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        int action = ev.getAction() & MotionEvent.ACTION_MASK;
//        switch (action){
//            case MotionEvent.ACTION_DOWN:
//                intercepted=false;
//                super.onInterceptTouchEvent(ev);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dX=x-mLastXIntercept;
//                int dY=y-mLastYIntercept;
//                if(Math.abs(dX)>Math.abs(dY)){
//                    intercepted=true;
//                }else{
//                    intercepted=false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                intercepted=false;
//                break;
//            default:
//                break;
//        }
//
//        mLastXIntercept=x;
//        mLastYIntercept=y;
//
//        return intercepted;


        //内部拦截法
        int action = ev.getAction();
        if(action==MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;


    }

}
