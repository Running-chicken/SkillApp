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
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private GridView gvDate;
    private TextView tvPrevious,tvNext,tvDate;
    private List<CalendarDateBean> mListDate = new ArrayList<>();
    private CalendarAdapter mAdapter;
    private Calendar calendar = Calendar.getInstance();
    private Calendar nowCalendar = Calendar.getInstance();
    private int nowYear;
    private int nowMonth;
    private int nowDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        tvPrevious.setOnClickListener(mOnClick);
        tvNext.setOnClickListener(mOnClick);
    }


    private void initView() {
        tvDate = findViewById(R.id.tv_date);
        tvNext = findViewById(R.id.tv_next);
        tvPrevious = findViewById(R.id.tv_previous);
        gvDate = findViewById(R.id.gv_date);
    }

    private void initData() {
        nowYear = nowCalendar.get(Calendar.YEAR);
        nowMonth = nowCalendar.get(Calendar.MONTH);
        nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        tvDate.setText(String.valueOf(year) + "年" + String.valueOf(month)+"月");

        mAdapter = new CalendarAdapter(mListDate);
        gvDate.setAdapter(mAdapter);
        refreshCaledar();
    }

    View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_previous:
                    lastMonth();
                    break;
                case R.id.tv_next:
                    nextMonth();
                    break;
            }
        }
    };

    public class CalendarAdapter extends BaseAdapter{

        List<CalendarDateBean> list;
        int months;
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

            CalendarDateBean bean = list.get(i);
            months = calendar.get(Calendar.MONTH);

            if(bean.year==nowYear && bean.month==nowMonth && bean.day==nowDay){
                holder.tvDate.setTextColor(getResources().getColor(R.color.white));
                holder.tvDate.setBackground(getResources().getDrawable(R.drawable.shape_circlr_blue_3295f6));
            }else{
                holder.tvDate.setTextColor(getResources().getColor(R.color.black_363e48));
                holder.tvDate.setBackground(null);
            }
            if(bean.month!=months){
                holder.tvDate.setTextColor(getResources().getColor(R.color.gray_c0c0c0));
                holder.tvDate.setBackground(null);
            }
            holder.tvDate.setText(list.get(i).day+"");

            return convertView;
        }

        class ViewHolder{
            TextView tvDate;

        }
    }


    /**
     * 上一个月
     */
    public void lastMonth() {
        calendar.add(Calendar.MONTH, -1);
        int years = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        tvDate.setText(String.valueOf(years) + "年" + String.valueOf(month)+"月");
        refreshCaledar();
    }



    /**
     * 下一月
     */
    public void nextMonth() {
        calendar.add(Calendar.MONTH, +1);
        int years = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        tvDate.setText(String.valueOf(years) + "年" + String.valueOf(month)+"月");
        refreshCaledar();
    }


    private void refreshCaledar() {

        //清除list数据

        if (mListDate != null) {
            mListDate.clear();
        }

        //将原有的calendar复制
        Calendar calendars = (Calendar) calendar.clone();
        //将日改为1号，以用来确定1号为星期几
        calendars.set(Calendar.DAY_OF_MONTH, 1);
        //获得1号的星期
        int weekday = calendars.get(Calendar.DAY_OF_WEEK) - 1;
        //偏移calendas
        calendars.add(Calendar.DAY_OF_MONTH, -weekday);
        int maxNumber = 6 * 7;
        while (mListDate.size() < maxNumber) {
            int yaer = calendars.get(Calendar.YEAR);
            int month = calendars.get(Calendar.MONTH);
            int day = calendars.get(Calendar.DAY_OF_MONTH);
            CalendarDateBean bean = new CalendarDateBean();
            bean.year = yaer;
            bean.month = month;
            bean.day = day;
            mListDate.add(bean);
            calendars.add(Calendar.DAY_OF_MONTH, 1);

        }

        mAdapter.notifyDataSetChanged();
    }
}
