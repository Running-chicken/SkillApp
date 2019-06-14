package com.cc.skillapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.skillapp.R;
import com.cc.skillapp.entity.CalendarEntity.CalendarDateBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private GridView gvDate;
    private ImageView ivPrevious,ivNext;
    private TextView tvDate;
    private List<CalendarDateBean> mListDate = new ArrayList<>();
    private CalendarAdapter mAdapter;
    private Calendar calendar = Calendar.getInstance();
    private Calendar nowCalendar = Calendar.getInstance();
    private int nowYear;
    private int nowMonth;
    private int nowDay;
    private TextView tvCalendarFirst,tvCalendarSecond,tvCalendarThird,tvCalendarFourth;
    private View vfourTh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        ivPrevious.setOnClickListener(mOnClick);
        ivNext.setOnClickListener(mOnClick);
    }


    private void initView() {
        tvDate = findViewById(R.id.tv_date);
        ivNext = findViewById(R.id.iv_next);
        ivPrevious = findViewById(R.id.iv_previous);
        gvDate = findViewById(R.id.gv_date);
        tvCalendarFirst = findViewById(R.id.tv_calendar_first);
        tvCalendarSecond = findViewById(R.id.tv_calendar_second);
        tvCalendarThird = findViewById(R.id.tv_calendar_third);
        tvCalendarFourth = findViewById(R.id.tv_calendar_fourth);
        vfourTh = findViewById(R.id.view_calendar_fourth);
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
                case R.id.iv_previous:
                    lastMonth();
                    break;
                case R.id.iv_next:
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
                holder.tvDate.setBackground(getResources().getDrawable(R.drawable.shape_circle_blue_3295f6));
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

        //将日期改为最后一天
        int maxMonthDays = calendars.getActualMaximum(Calendar.DATE);
        calendars.set(Calendar.DAY_OF_MONTH,maxMonthDays);
        Log.i("cuican","当月最大天数："+maxMonthDays);

        int endWeekday = calendars.get(Calendar.DAY_OF_WEEK)-1;
        Log.i("cuican","当月最后一天星期几："+endWeekday);

        //将日改为1号，以用来确定1号为星期几
        calendars.set(Calendar.DAY_OF_MONTH, 1);
        //获得1号的星期
        int weekday = calendars.get(Calendar.DAY_OF_WEEK) - 1;
        Log.i("cuican","当月第一天星期几："+weekday);
        //偏移calendas
        calendars.add(Calendar.DAY_OF_MONTH, -weekday);

        //下月补充天数
        endWeekday = 7- (endWeekday+1);
        //一共需要展示的天数： 上月补充天数+当月天数+下月补充天数
        int maxNumber = weekday+maxMonthDays+endWeekday;
        Log.i("cuican","上月几天："+weekday+" 当月："+maxMonthDays+" 下月："+endWeekday);
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
