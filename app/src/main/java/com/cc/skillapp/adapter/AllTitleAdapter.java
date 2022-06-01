package com.cc.skillapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllApplicationEntity;
import com.cc.library.base.util.StringUtils;
import com.cc.library.base.util.Utils;

import java.util.List;

public class AllTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AllApplicationEntity.AllAppMenu> mList;
    private OnTitleItemListener mOnTitleItemListener;

    public AllTitleAdapter(Context context, List<AllApplicationEntity.AllAppMenu> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_title, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Holder){
            AllApplicationEntity.AllAppMenu bean = mList.get(position);
            if (!StringUtils.isNullOrEmpty(bean.reponame)){
                ((Holder) holder).mTvTitle.setText(bean.reponame);
            }
            if (bean.isCheck){
                ((Holder) holder).mTvTitle.setTypeface(((Holder) holder).mTvTitle.getTypeface(), Typeface.BOLD);
                ((Holder) holder).mViewLine.setVisibility(View.VISIBLE);
            }else {
                ((Holder) holder).mTvTitle.setTypeface(Typeface.DEFAULT);
                ((Holder) holder).mViewLine.setVisibility(View.INVISIBLE);
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((Holder) holder).mLlAllTitle.getLayoutParams();
            if (position == 0){
                layoutParams.leftMargin = Utils.dip2px(mContext,20);
            }else {
                layoutParams.leftMargin = Utils.dip2px(mContext,30);
            }
            if (position == getItemCount() - 1){
                layoutParams.rightMargin = Utils.dip2px(mContext,20);
            }else {
                layoutParams.rightMargin = Utils.dip2px(mContext,0);
            }
            ((Holder) holder).mLlAllTitle.setLayoutParams(layoutParams);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTitleItemListener != null){
                        mOnTitleItemListener.onTitleItemListener(position);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        if (holder instanceof Holder){
            for (Object payload:payloads) {
                switch (String.valueOf(payload)){
                    case "checked":
                        mList.get(position).isCheck = true;
                        ((Holder) holder).mTvTitle.setTypeface(((Holder) holder).mTvTitle.getTypeface(), Typeface.BOLD);
                        ((Holder) holder).mViewLine.setVisibility(View.VISIBLE);
                        break;
                    case "unchecked":
                        mList.get(position).isCheck = false;
                        ((Holder) holder).mTvTitle.setTypeface(Typeface.DEFAULT);
                        ((Holder) holder).mViewLine.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnTitleItemListener(OnTitleItemListener onTitleItemListener){
        mOnTitleItemListener = onTitleItemListener;
    }

    /**
     * item的点击事件
     */
    public interface OnTitleItemListener{
        void onTitleItemListener(int position);
    }

    class Holder extends RecyclerView.ViewHolder{

        LinearLayout mLlAllTitle;
        TextView mTvTitle;
        View mViewLine;

        public Holder(View itemView) {
            super(itemView);
            mLlAllTitle = itemView.findViewById(R.id.ll_all_title);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mViewLine = itemView.findViewById(R.id.view_line);
        }
    }

}
