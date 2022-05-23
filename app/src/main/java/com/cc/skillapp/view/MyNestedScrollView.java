package com.cc.skillapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    int scrollY = 0;
    int scrollX = 0;
    boolean toIntercept = false;

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return toIntercept;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            scrollY = (int) ev.getY();
            scrollX = (int) ev.getX();
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            if(Math.abs(scrollX-ev.getX()) > Math.abs(scrollY-ev.getY())){
                toIntercept = false;
                return false;
            }else{
                toIntercept = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
