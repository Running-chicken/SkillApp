package com.cc.skillapp.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class TestListView extends ListView {
    public TestListView(Context context) {
        super(context);
    }

    public TestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastXIntercept;
    private int mLastYIntercept;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int dX=x-mLastXIntercept;
                int dY=y-mLastYIntercept;
                if(Math.abs(dX)>Math.abs(dY)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            default:
                break;
        }

        mLastXIntercept=x;
        mLastYIntercept=y;

        return super.dispatchTouchEvent(ev);

    }
}
