package com.cc.module.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.cc.library.base.util.Utils;
import com.cc.module.test.R;


public class TestMyView extends View {

    private Context context;
    private float height;
    private float width;
    private String text;
    Paint mPaint;


    public TestMyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.myTestView);

        height = typedArray.getDimension(R.styleable.myTestView_cc_height,0);
        width = typedArray.getDimension(R.styleable.myTestView_cc_width,0);
        text = typedArray.getString(R.styleable.myTestView_cc_content);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Utils.dip2px(context,width),Utils.dip2px(context,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(30);
        canvas.drawRect(0,0,100,250,mPaint);
        canvas.drawText(text,0,50,mPaint);
    }
}
