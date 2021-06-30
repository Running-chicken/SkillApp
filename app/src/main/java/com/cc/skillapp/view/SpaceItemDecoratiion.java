package com.cc.skillapp.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoratiion extends RecyclerView.ItemDecoration {

    /**
     * 设置recyclerview分割线，可自定义设置边距
     */

    private int space;

    public SpaceItemDecoratiion(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
        outRect.top =space;
    }
}
