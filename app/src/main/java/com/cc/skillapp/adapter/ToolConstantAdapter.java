package com.cc.skillapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cc.skillapp.R;
import com.cc.skillapp.entity.AllApplicationEntity;
import com.cc.skillapp.utils.StringUtils;


import java.util.List;

public class ToolConstantAdapter extends BaseAdapter {

    private Context mContext;
    private List<AllApplicationEntity.Submenu> mList;

    public ToolConstantAdapter(Context context, List<AllApplicationEntity.Submenu> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_constant_tool,null);
            holder = new Holder();
            holder.iv_constant_tool = convertView.findViewById(R.id.iv_constant_tool);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        AllApplicationEntity.Submenu bean = mList.get(position);
        if (!StringUtils.isNullOrEmpty(bean.logo)){
            Glide.with(mContext)
                    .load(bean.logo)
                    .error(R.drawable.application_default)
                    .fallback(R.drawable.application_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_constant_tool);
        }
        return convertView;
    }

    class Holder {
        ImageView iv_constant_tool;
    }

}
