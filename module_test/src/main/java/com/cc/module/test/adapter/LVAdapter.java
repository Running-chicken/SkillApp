package com.cc.module.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.library.base.util.Utils;
import com.cc.module.test.R;

import java.util.List;

public class LVAdapter extends BaseAdapter {

    Context context;
    List<String> list;
    public LVAdapter(Context context,List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder = new Holder();
            holder.id = position;
            convertView = LayoutInflater.from(context).inflate(R.layout.test_item_lv,parent,false);
            holder.tvItem = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
            Utils.log(getClass(),"null convertView "+position);
        }else{
            holder = (Holder) convertView.getTag();
            Utils.log(getClass(),"convertView "+ holder.id);
        }


        holder.tvItem.setText(list.get(position));

        return convertView;
    }

    class Holder{
        int id;
        TextView tvItem;
    }
}
