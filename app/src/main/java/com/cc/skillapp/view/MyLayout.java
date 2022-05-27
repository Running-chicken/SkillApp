package com.cc.skillapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLayout extends LinearLayout {

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    int lastX=0;
    int lastY=0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //外部拦截法
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) ev.getX();
//                lastY = (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dx = (int) (ev.getX()-lastX);
//                int dy= (int) (ev.getY()-lastY);
//                if(Math.abs(dx)>Math.abs(dy)){
//                    return true;
//                }
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);


        //内部拦截法
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
