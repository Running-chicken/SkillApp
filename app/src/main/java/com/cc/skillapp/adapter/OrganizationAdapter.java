package com.cc.skillapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.DeptEntity.DeptItemEntity;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DeptItemEntity> clueList;
    private OnItemOperationListener onItemOperationListener;
    private LayoutInflater inflater;
    private boolean isLastItemShow = false;


    public OrganizationAdapter(Context context, List<DeptItemEntity> clueList, OnItemOperationListener onItemOperationListener) {
        this.context = context;
        this.clueList = clueList;
        this.onItemOperationListener = onItemOperationListener;
        inflater = LayoutInflater.from(context);
    }

    public void setIsLastItem(boolean isLast) {
        isLastItemShow = isLast;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dept,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DeptItemEntity entity = clueList.get(position);



        viewHolder.tvDeptName.setText(entity.deptName);

        viewHolder.tvDeptName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemOperationListener.onItemClick(entity,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clueList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDeptName;


        public ViewHolder(View itemView) {
            super(itemView);


            tvDeptName = (TextView)itemView.findViewById(R.id.tv_dept_name);
        }
    }


    public interface OnItemOperationListener{
        void onItemClick(DeptItemEntity entity, int position);

    }

}
