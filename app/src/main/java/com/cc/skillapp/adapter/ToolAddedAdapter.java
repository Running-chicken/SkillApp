package com.cc.skillapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllApplicationEntity;
import com.example.library_base.util.StringUtils;
import com.example.library_base.util.Utils;
import com.cc.skillapp.view.DragedRecycleView.ItemTouchHelperAdapter;
import com.cc.skillapp.view.DragedRecycleView.ItemTouchHelperViewHolder;

import java.util.List;

public class ToolAddedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperAdapter {

    private Context mContext;
    private List<AllApplicationEntity.Submenu> mList;
    private OnAddedItemLongListener mOnAddedItemLongListener;
    private OnAddedItemListener mOnAddedItemListener;

    public ToolAddedAdapter(Context context, List<AllApplicationEntity.Submenu> list){
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tool_added, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            final AllApplicationEntity.Submenu bean = mList.get(position);
            if (!StringUtils.isNullOrEmpty(bean.functionid)){
                ((ViewHolder) holder).mIvAddedIcon.setImageResource(Utils.getImageId(mContext,bean.logo));
                if (!StringUtils.isNullOrEmpty(bean.reponame)){
                    ((ViewHolder) holder).mTvAddedTitle.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).mTvAddedTitle.setText(bean.reponame);
                }else {
                    ((ViewHolder) holder).mTvAddedTitle.setVisibility(View.GONE);
                }
                if(!"2".equals(bean.functionid)){
                    ((ViewHolder) holder).mIvAddedDelete.setVisibility(View.VISIBLE);
                }else{
                    ((ViewHolder) holder).mIvAddedDelete.setVisibility(View.INVISIBLE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnAddedItemListener != null && !"2".equals(bean.functionid)){ //暂时确定任务不可点击
                            mOnAddedItemListener.onAddedItemListener(bean);
                        }
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mOnAddedItemLongListener != null && !"2".equals(bean.functionid)){
                            mOnAddedItemLongListener.onDragStarted(holder);
                        }
                        return true;
                    }
                });
            }else {
                ((ViewHolder) holder).mTvAddedTitle.setVisibility(View.GONE);
                ((ViewHolder) holder).mIvAddedIcon.setImageResource(R.drawable.all_application_empty);
                ((ViewHolder) holder).mIvAddedDelete.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        try {
            if (!StringUtils.isNullOrEmpty(mList.get(toPosition).functionid) && !"2".equals(mList.get(toPosition).functionid)){
                AllApplicationEntity.Submenu bean = mList.remove(fromPosition);
                mList.add(toPosition > fromPosition ? toPosition  : toPosition, bean);
                notifyItemMoved(fromPosition, toPosition);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemClear() {
        notifyDataSetChanged();//松手的时候刷新下列表
    }

    public void setAddedLongListener(OnAddedItemLongListener onAddedItemLongListener){
        mOnAddedItemLongListener = onAddedItemLongListener;
    }

    public void setAddedListener(OnAddedItemListener onAddedItemListener){
        mOnAddedItemListener = onAddedItemListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        ImageView mIvAddedIcon;
        ImageView mIvAddedDelete;
        TextView mTvAddedTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvAddedIcon = itemView.findViewById(R.id.iv_added_icon);
            mIvAddedDelete = itemView.findViewById(R.id.iv_added_delete);
            mTvAddedTitle = itemView.findViewById(R.id.tv_added_title);
        }

        @Override
        public void onItemSelected() {
            //ViewHolder实现ItemTouchHelperViewHolder的方法，暂未使用
        }

        @Override
        public void onItemClear() {
            //ViewHolder实现ItemTouchHelperViewHolder的方法，暂未使用
        }
    }

    /**
     * item长按的点击事件
     */
    public interface OnAddedItemLongListener{
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    /**
     * item的点击事件
     */
    public interface OnAddedItemListener{
        void onAddedItemListener(AllApplicationEntity.Submenu bean);
    }

}
