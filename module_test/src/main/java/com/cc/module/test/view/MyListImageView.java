package com.cc.module.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.cc.module.test.R;

public class MyListImageView extends HorizontalScrollView {
    Context mContext;
    int maxSize;
    float textSize;
    String text;

    public MyListImageView(Context context) {
        this(context,null);
    }

    public MyListImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.myImageList);
        maxSize = typedArray.getInt(R.styleable.myImageList_max_size,0);
        textSize = typedArray.getDimension(R.styleable.myImageList_text_size,0);
        text = typedArray.getString(R.styleable.myImageList_text);

        init();

        typedArray.recycle();
    }

    private void init() {

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<maxSize;i++){
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.mipmap.home_product_ad);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);

        }

        this.addView(linearLayout);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        canvas.drawText(text,300,200,paint);
    }
}
