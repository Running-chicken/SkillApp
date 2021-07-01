package com.cc.skillapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllApplicationEntity;
import com.cc.skillapp.utils.StringUtils;

import java.util.List;

public class AllApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AllApplicationEntity.AllAppMenu> mList;
    private boolean mIsEditing;
    private OnAllApplicationItemClickListener mOnAllApplicationItemClickListener;

    public AllApplicationAdapter(Context context, List<AllApplicationEntity.AllAppMenu> list, boolean isEditing){
        mContext = context;
        mList = list;
        mIsEditing = isEditing;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_application, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Holder){
            ViewGroup.LayoutParams layoutParams = ((Holder) holder).mLlRoot.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //当只getItemCount = 1 时，如果屏幕显示不下全部，会导致高度异常无法滑动，需要使用warp_content
            if (position == getItemCount() - 1 && position != 0){
                ((Holder) holder).mViewEmpty.setVisibility(View.VISIBLE);
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ((Holder) holder).mLlRoot.setLayoutParams(layoutParams);
            }else {
                ((Holder) holder).mViewEmpty.setVisibility(View.GONE);
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                ((Holder) holder).mLlRoot.setLayoutParams(layoutParams);
            }
            AllApplicationEntity.AllAppMenu bean = mList.get(position);
            if (!StringUtils.isNullOrEmpty(bean.reponame)){
                ((Holder) holder).mTvTitle.setText(bean.reponame);
            }
            if (bean.submenu != null){
                ApplicationItemAdapter mAdapter = new ApplicationItemAdapter(mContext,bean.submenu,mIsEditing);
                mAdapter.setOnApplicationItemListener(new ApplicationItemAdapter.OnApplicationItemListener() {

                    @Override
                    public void onApplicationItemListener(AllApplicationEntity.Submenu sBean, int index, View view) {
                        if (mOnAllApplicationItemClickListener != null){
                            mOnAllApplicationItemClickListener.onAllApplicationItemListener(sBean,position,index,view);
                        }
                    }

                });
                ((Holder) holder).mRvApplication.setAdapter(mAdapter);
            }
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
                int index = Integer.parseInt(String.valueOf(payload));
                ((Holder) holder).mRvApplication.getAdapter().notifyItemChanged(index,"checked");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setIsEditing(boolean isEditing){
        mIsEditing = isEditing;
        notifyDataSetChanged();
    }

    public void setOnAllApplicationItemClickListener(OnAllApplicationItemClickListener onAllApplicationItemClickListener){
        mOnAllApplicationItemClickListener = onAllApplicationItemClickListener;
    }

    /**
     * item的点击事件
     */
    public interface OnAllApplicationItemClickListener{
        //View参数暂时没用，以后添加动画可能用到
        void onAllApplicationItemListener(AllApplicationEntity.Submenu bean, int position, int index, View view);
    }

    class Holder extends RecyclerView.ViewHolder {

        LinearLayout mLlRoot;
        TextView mTvTitle;
        RecyclerView mRvApplication;
        View mViewEmpty;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mLlRoot = itemView.findViewById(R.id.ll_root);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mRvApplication = itemView.findViewById(R.id.rv_application);
            GridLayoutManager manager = new GridLayoutManager(itemView.getContext(),4);
            mRvApplication.setLayoutManager(manager);
            //用来占位，最后一条显示，避免刷新item时导致布局移动问题
            mViewEmpty = itemView.findViewById(R.id.view_empty);
        }
    }

}
