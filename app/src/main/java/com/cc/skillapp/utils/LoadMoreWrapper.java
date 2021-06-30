package com.cc.skillapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cc.skillapp.R;

/**
 * 封装上拉加载更多
 */

public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter adapter;

    private final int TYPE_ITEM = 1;

    private final int TYPE_FOOTER = 2;

    private int loadState = 2;

    public final int LOADING = 1;

    public final int LOADING_COMPLETE = 2;

    public final int LOADING_END = 3;

    public LoadMoreWrapper(RecyclerView.Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view,null);
            return new FootViewHolder(view);
        }else{
            return adapter.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState){
                case LOADING:
                    footViewHolder.tvFooter.setVisibility(View.VISIBLE);
                    footViewHolder.tvFooter.setText("正在加载...");
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.tvFooter.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    footViewHolder.tvFooter.setVisibility(View.VISIBLE);
                    footViewHolder.tvFooter.setText("到底了");
                    break;
            }
        }else{
            adapter.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount()+1;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if(layoutParams!=null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int position = holder.getLayoutPosition();
            if(getItemViewType(position) == TYPE_FOOTER){
                params.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder{

        TextView tvFooter;

        public FootViewHolder(View itemView) {
            super(itemView);
            tvFooter = itemView.findViewById(R.id.tv_footer);
        }
    }

    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
