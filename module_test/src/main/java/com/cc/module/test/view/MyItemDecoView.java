package com.cc.module.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecoView extends RecyclerView.ItemDecoration {


    private int itemSize = 10;

    private Paint paint;

    private Context context;


    public MyItemDecoView(Context context){
        this.context = context;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    //绘制一个分割线
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childSize = parent.getChildCount();
        for(int i=0;i<childSize;i++){
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom()+layoutParams.bottomMargin;
            int bottom = top+itemSize;
            c.drawRect(left,top,right,bottom,paint);
        }

    }


    //设施padding
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0,0,0,0);
    }
}
