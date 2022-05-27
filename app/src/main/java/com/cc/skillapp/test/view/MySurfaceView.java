package com.cc.skillapp.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;

    private Paint mPaint;
    private Path mPath;
    private float mLastX, mLastY;


    public MySurfaceView(Context context) {
        this(context,null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        // 设置可以获取焦点
        setFocusable(true);
        // 设置触摸可以获取焦点
        setFocusableInTouchMode(true);
        // 保持屏幕常亮
        this.setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mIsDrawing = true;
        // 通过线程池来执行
        Executors.newCachedThreadPool().execute(this);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            drawing();
        }
    }

    private void drawing() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if(mCanvas!=null){
                mCanvas.drawColor(Color.WHITE);
                mCanvas.drawPath(mPath,mPaint);
            }
        } finally {
            if(mCanvas!=null){
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x-mLastX);
                float dy = Math.abs(y- mLastY);
                if(dx>=3 || dy>=3){
                    mPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mLastX=x;
                mLastY=y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
