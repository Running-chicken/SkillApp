package com.cc.module.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cc.library.base.util.StringUtils;
import com.cc.module.test.R;
import com.cc.module.test.activity.ViewPager2Activity;
import com.cc.module.test.activity.ViewPager2Activity.Pic;

import java.util.List;

public class MyVp2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Pic> mList;

    public MyVp2Adapter(Context context,List<Pic> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.test_layout_vp2_item,parent,false));
        }else{
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.test_layout_fg1,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!StringUtils.isNullOrEmpty(mList.get(position).name)){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolder){
            ((MyHolder) holder).tvItem.setText(mList.get(position).name);
        }else if(holder instanceof MyViewHolder){
            Glide.with(context).load(mList.get(position).url).into(((MyViewHolder) holder).ivItem);
        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvItem;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_vp_item);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivItem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.iv_image_item);
        }
    }

}
