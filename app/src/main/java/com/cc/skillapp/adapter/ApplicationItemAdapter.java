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

import java.util.List;

public class ApplicationItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AllApplicationEntity.Submenu> mList;
    private boolean mIsEditing;
    private OnApplicationItemListener mOnApplicationItemListener;

    public ApplicationItemAdapter(Context context, List<AllApplicationEntity.Submenu> list, boolean isEditing){
        mContext = context;
        mList = list;
        mIsEditing = isEditing;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_application, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder){
            AllApplicationEntity.Submenu bean = mList.get(position);
            ((Holder) holder).mIvApplicationIcon.setImageResource(Utils.getImageId(mContext,bean.logo));
            if (!StringUtils.isNullOrEmpty(bean.reponame)){
                ((Holder) holder).mTvApplicationTitle.setText(bean.reponame);
            }
            setEditButtonState(holder, position, bean);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        if (holder instanceof Holder){
            AllApplicationEntity.Submenu bean = mList.get(position);
            for (Object payload:payloads) {
                switch (String.valueOf(payload)){
                    case "checked":
                        setEditButtonState(holder, position, bean);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void setEditButtonState(@NonNull final RecyclerView.ViewHolder holder, final int position, final AllApplicationEntity.Submenu bean) {
        if (mIsEditing && !StringUtils.isNullOrEmpty(bean.iscanedit) && "1".equals(bean.iscanedit)){
            ((Holder) holder).mIvApplicationEdit.setVisibility(View.VISIBLE);
            if (bean.isAddOrRemove){
                ((Holder) holder).mIvApplicationEdit.setImageResource(R.drawable.all_application_delete);
            }else {
                ((Holder) holder).mIvApplicationEdit.setImageResource(R.drawable.all_application_add);
            }
        }else {
            ((Holder) holder).mIvApplicationEdit.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnApplicationItemListener != null){
                    mOnApplicationItemListener.onApplicationItemListener(bean,position,holder.itemView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnApplicationItemListener(OnApplicationItemListener onApplicationItemListener){
        mOnApplicationItemListener = onApplicationItemListener;
    }

    /**
     * item的点击事件
     */
    public interface OnApplicationItemListener{
        void onApplicationItemListener(AllApplicationEntity.Submenu sBean, int index, View view);
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView mIvApplicationIcon;
        ImageView mIvApplicationEdit;
        TextView mTvApplicationTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mIvApplicationIcon = itemView.findViewById(R.id.iv_application_icon);
            mIvApplicationEdit = itemView.findViewById(R.id.iv_application_edit);
            mTvApplicationTitle = itemView.findViewById(R.id.tv_application_title);
        }

    }
}
