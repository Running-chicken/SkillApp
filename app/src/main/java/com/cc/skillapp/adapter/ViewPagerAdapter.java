package com.cc.skillapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cc.skillapp.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<String> list;
    View view;
    ImageView ivVp;
    Context mContext;

    public ViewPagerAdapter (Context mContext,List<String> list){
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_vp,null);
        ivVp = view.findViewById(R.id.iv_vp);
        Glide.with(mContext).load(list.get(position % list.size())).centerCrop().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivVp);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
