package com.cc.library.base.adapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BaseBindingAdapter {

    @BindingAdapter("textNew")
    public static void setTextNew(TextView textNew,String text){
        textNew.setText("使用textNew:"+text);
    }



}
