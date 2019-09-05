package com.cc.skillapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllDataEntity;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<AllDataEntity> list;


    public MyRvAdapter(Context mContext, List<AllDataEntity> list){
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).tvTitle.setText(list.get(position).author);
            ((MyViewHolder) holder).tvContent.setText(list.get(position).title);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
