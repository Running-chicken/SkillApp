package com.cc.skillapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.CalendarEntity.CalendarDateBean;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private GridView gvDate;
    private TextView tvPrevious,tvNext,tvDate;
    private List<CalendarDateBean> mListDate = new ArrayList<>();
    private CalendarAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initView();
        initData();
    }



    private void initView() {
        tvDate = findViewById(R.id.tv_date);
        tvNext = findViewById(R.id.tv_next);
        tvPrevious = findViewById(R.id.tv_previous);
        gvDate = findViewById(R.id.gv_date);
    }

    private void initData() {
        for(int i=0;i<30;i++){
            CalendarDateBean bean = new CalendarDateBean();
            bean.date = i+"";
            mListDate.add(bean);
        }
        mAdapter = new CalendarAdapter(mListDate);
        gvDate.setAdapter(mAdapter);

    }

    public class CalendarAdapter extends BaseAdapter{

        List<CalendarDateBean> list;
        public CalendarAdapter(List<CalendarDateBean> mListDate){
            this.list = mListDate;
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(convertView==null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.item_calendar,null);
                holder.tvDate = convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvDate.setText(list.get(i).date);

            return convertView;
        }

        class ViewHolder{
            TextView tvDate;

        }
    }

}
