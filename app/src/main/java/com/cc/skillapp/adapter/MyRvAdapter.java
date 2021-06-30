package com.cc.skillapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllDataEntity;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<AllDataEntity> list;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    /**加载状态 默认加载完*/
    private int loadState = 2;
    /**正在加载*/
    public final int LOADING = 1;
    /**加载完成*/
    public final int LOADING_COMPLETED = 2;
    /**到底了*/
    public final int LOADING_END = 3;


    public MyRvAdapter(Context mContext, List<AllDataEntity> list){
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv,null);
            return new MyViewHolder(view);
        }else if(viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_footer_view,null);
            return new FooterViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).tvTitle.setText(list.get(position).author);
            ((MyViewHolder) holder).tvContent.setText(list.get(position).title);
            ((MyViewHolder) holder).tvTime.setText(list.get(position).date);
        }else if(holder instanceof FooterViewHolder){
            switch (loadState){
                case LOADING:
                    ((FooterViewHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).tvFooter.setText("正在加载...");
                    break;
                case LOADING_COMPLETED:
                    ((FooterViewHolder) holder).tvFooter.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    ((FooterViewHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).tvFooter.setText("到底了");
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1==getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
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

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        TextView tvFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvFooter = itemView.findViewById(R.id.tv_footer);
        }
    }

}
