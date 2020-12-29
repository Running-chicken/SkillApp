package com.cc.skillapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.skillapp.R;

public class MyShowViewLayout extends LinearLayout {

    private String rightText;
    private LayoutInflater layoutInflater;
    private TextView tvRightText;


    public MyShowViewLayout(Context context) {
        super(context);
        init(context,null);
    }

    public MyShowViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MyShowViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyShowViewLayout);
        rightText = typedArray.getString(R.styleable.MyShowViewLayout_right_text);
        typedArray.recycle();

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_show_view,this);
        tvRightText = findViewById(R.id.tv_text_right);



    }




    public void setRightText(String text){
        tvRightText.setText(text);
    }

}
