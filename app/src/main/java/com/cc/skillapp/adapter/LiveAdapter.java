package com.cc.skillapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllDataEntity;

import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AllDataEntity> list;

    public LiveAdapter(List<AllDataEntity> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tvTitle.setText(list.get(position).author);
        ((MyViewHolder) holder).tvContent.setText(list.get(position).title);
        ((MyViewHolder) holder).tvTime.setText(list.get(position).date);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }


}
