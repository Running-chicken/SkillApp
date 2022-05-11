package com.cc.skillapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.TestLisTEntity.MenuIcon;

import java.util.List;

public class MenuIconAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MenuIcon> list;

    public MenuIconAdapter(List<MenuIcon> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tvTitle.setText(list.get(position).permiName);
        ((MyViewHolder) holder).tvContent.setText(list.get(position).permiIcon);
        ((MyViewHolder) holder).tvTime.setText(list.get(position).permiId);


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
