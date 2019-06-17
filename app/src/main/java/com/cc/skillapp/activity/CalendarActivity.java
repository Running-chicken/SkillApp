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
import com.cc.skillapp.entity.CalendarEntity;
import com.cc.skillapp.entity.CalendarEntity.CalendarDateBean;
import com.cc.skillapp.entity.LiveCalendarEntity;
import com.cc.skillapp.entity.LiveCalendarEntity.LiveCalendarBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalendarActivity extends AppCompatActivity {

    private GridView gvDate;
    private ImageView ivPrevious,ivNext;
    private TextView tvDate;
    private List<CalendarDateBean> mListMonthDate = new ArrayList<>();
    private CalendarAdapter mAdapter;
    private Calendar calendar = Calendar.getInstance();
    private Calendar nowCalendar = Calendar.getInstance();
    private int nowYear;
    private int nowMonth;
    private int nowDay;
    private TextView tvCalendarFirst,tvCalendarSecond,tvCalendarThird,tvCalendarFourth;
    private View vfourTh;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateFormatMonth;
    private Map<String,CalendarDateBean> mMapMsg = new HashMap<>();
    private Map<String, LiveCalendarBean> mMapLiveMsg = new HashMap<>();
    private String requestMonth;
    private TextView tvTPRL,tvZBRL;
    private View viewTPRL,viewZBRL;
    /**土拍：0  直播：1*/
    private int type;


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
        tvTPRL.setOnClickListener(mOnClick);
        tvZBRL.setOnClickListener(mOnClick);
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
        tvTPRL = findViewById(R.id.tv_tprl);
        tvZBRL = findViewById(R.id.tv_zbrl);
        viewTPRL = findViewById(R.id.view_tprl);
        viewZBRL = findViewById(R.id.view_zbrl);

    }

    private void initData() {
        type = 0;
        tvCalendarFirst.setText("成交");
        tvCalendarSecond.setText("公告");
        tvCalendarThird.setText("截止");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatMonth = new SimpleDateFormat("yyyy-MM");
        nowYear = nowCalendar.get(Calendar.YEAR);
        nowMonth = nowCalendar.get(Calendar.MONTH);
        nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        tvDate.setText(String.valueOf(year) + "年" + String.valueOf(month)+"月");
        requestMonth = dateFormatMonth.format(calendar.getTime());

        mAdapter = new CalendarAdapter(mListMonthDate);
        gvDate.setAdapter(mAdapter);
        refreshCaledar();
    }

    private void updateMsg(){
        requestMonth = dateFormatMonth.format(calendar.getTime());
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        final Map<String,String> map = new HashMap<>();
        map.put("cityCode","110100");
        map.put("month",requestMonth);
        map.put("ownerId","standard");
        map.put("cityName","北京");
        String json  = new Gson().toJson(map);

        formBody.add("json",json);
        formBody.add("messagename","tdy_GetCalendarIndexData");
        formBody.add("wirelesscode","966214B63AD14448D4252C6621C49408");
        /**
         * 测试地址
         * http://124.251.47.220:8021/land/agentservice.jsp?
         * 正式地址
         * http://124.251.47.220:8021/land/agentservice.jsp?messagename=tdy_GetLiveBroadIndexListData&json={
         *   "categoryNames": "土地直播",
         *   "cityName": "北京",
         *   "endTime": "2019-01-02",
         *   "startTime": "2019-01-01"
         * }&wirelesscode=0D9EC087A28184179A094F886075E8A8
         *
         */


        final Request request = new Request.Builder()
                .url("http://124.251.47.220:8021/land/agentservice.jsp?")
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonStr = response.body().string();

                    CalendarEntity entity = null;
                    try {
                        entity = new Gson().fromJson(jsonStr, CalendarEntity.class);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                    mMapMsg.clear();
                    if(entity!=null && entity.data!=null && entity.data.size()>0){
                        for(int i=0;i<entity.data.size();i++){
                            mMapMsg.put(entity.data.get(i).date,entity.data.get(i));
                        }

                    }


                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void updateZbMsg(){
        requestMonth = dateFormatMonth.format(calendar.getTime());
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        final Map<String,String> map = new HashMap<>();
        map.put("categoryNames","土地直播");
        map.put("cityName","北京");
        map.put("startTime","2019-05-01");
        map.put("endTime","2019-05-31");
        String json  = new Gson().toJson(map);

        formBody.add("json",json);
        formBody.add("messagename","tdy_GetLiveBroadIndexListData");
        formBody.add("wirelesscode","0D9EC087A28184179A094F886075E8A8");



        final Request request = new Request.Builder()
                .url("http://124.251.47.220:8021/land/agentservice.jsp?")
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonStr = response.body().string();
                    Log.i("cuican",jsonStr);

                    LiveCalendarEntity result = new Gson().fromJson(jsonStr,LiveCalendarEntity.class);
                    if(result!=null && result.data!=null && result.data.size()>0){
                        for(int i=0;i<result.data.size();i++){
                            mMapLiveMsg.put(result.data.get(i).startTime,result.data.get(i));
                        }
                    }

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
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
                case R.id.tv_tprl:
                    viewTPRL.setVisibility(View.VISIBLE);
                    viewZBRL.setVisibility(View.GONE);
                    tvTPRL.setTextColor(getResources().getColor(R.color.black_0e131a));
                    tvZBRL.setTextColor(getResources().getColor(R.color.gray_a4a7a9));
                    type=0;
                    tvCalendarFourth.setVisibility(View.VISIBLE);
                    vfourTh.setVisibility(View.VISIBLE);
                    tvCalendarFirst.setText("成交");
                    tvCalendarSecond.setText("公告");
                    tvCalendarThird.setText("截止");
                    refreshCaledar();
                    break;
                case R.id.tv_zbrl:
                    viewTPRL.setVisibility(View.GONE);
                    viewZBRL.setVisibility(View.VISIBLE);
                    tvTPRL.setTextColor(getResources().getColor(R.color.gray_a4a7a9));
                    tvZBRL.setTextColor(getResources().getColor(R.color.black_0e131a));
                    type=1;
                    tvCalendarFirst.setText("大咖讲堂");
                    tvCalendarSecond.setText("待拍地块");
                    tvCalendarThird.setText("土地推荐");
                    tvCalendarFourth.setVisibility(View.GONE);
                    vfourTh.setVisibility(View.GONE);
                    refreshCaledar();
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
                holder.viewFirst = convertView.findViewById(R.id.view_first);
                holder.viewSecond = convertView.findViewById(R.id.view_second);
                holder.viewThird = convertView.findViewById(R.id.view_third);
                holder.viewFourth = convertView.findViewById(R.id.view_fourth);
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

            if(type==0){
                if(mMapMsg.containsKey(bean.date)){
                    CalendarDateBean dateBean = mMapMsg.get(bean.date);
                    if(dateBean.hasClosing){
                        holder.viewFirst.setVisibility(View.VISIBLE);
                    }else{
                        holder.viewFirst.setVisibility(View.GONE);
                    }
                    if(dateBean.hasAnnouncement){
                        holder.viewSecond.setVisibility(View.VISIBLE);
                    }else{
                        holder.viewSecond.setVisibility(View.GONE);
                    }
                    if(dateBean.hasCompletetion){
                        holder.viewThird.setVisibility(View.VISIBLE);
                    }else{
                        holder.viewThird.setVisibility(View.GONE);
                    }
                    if(dateBean.hasPassIn){
                        holder.viewFourth.setVisibility(View.VISIBLE);
                    }else{
                        holder.viewFourth.setVisibility(View.GONE);
                    }

                }else{
                    holder.viewFirst.setVisibility(View.GONE);
                    holder.viewSecond.setVisibility(View.GONE);
                    holder.viewThird.setVisibility(View.GONE);
                    holder.viewFourth.setVisibility(View.GONE);
                }
            }else{
                holder.viewFirst.setVisibility(View.GONE);
                holder.viewSecond.setVisibility(View.GONE);
                holder.viewThird.setVisibility(View.GONE);
                holder.viewFourth.setVisibility(View.GONE);
                if(mMapLiveMsg.containsKey(bean.date)){
                    LiveCalendarBean dateBean = mMapLiveMsg.get(bean.date);
                    if(dateBean.liveBroadDetailInfoList!=null && dateBean.liveBroadDetailInfoList.size()>0){
                        for(int j=0;j<dateBean.liveBroadDetailInfoList.size();j++){
                            if("大咖讲堂".equals(dateBean.liveBroadDetailInfoList.get(j).tagNames)){
                                holder.viewFirst.setVisibility(View.VISIBLE);
                            }
                            if("待拍地块".equals(dateBean.liveBroadDetailInfoList.get(j).tagNames)){
                                holder.viewSecond.setVisibility(View.VISIBLE);
                            }
                            if("土地推荐".equals(dateBean.liveBroadDetailInfoList.get(j).tagNames)){
                                holder.viewThird.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                }
            }


            return convertView;
        }

        class ViewHolder{
            TextView tvDate;
            View viewFirst; //成交 or 大咖讲堂
            View viewSecond; //公告 or 待拍地块
            View viewThird; //截止 or 土地推介
            View viewFourth;//流拍

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

        if (mListMonthDate != null) {
            mListMonthDate.clear();
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
        while (mListMonthDate.size() < maxNumber) {
            String date = dateFormat.format(calendars.getTime());
            int yaer = calendars.get(Calendar.YEAR);
            int month = calendars.get(Calendar.MONTH);
            int day = calendars.get(Calendar.DAY_OF_MONTH);
            CalendarDateBean bean = new CalendarDateBean();
            bean.year = yaer;
            bean.month = month;
            bean.day = day;
            bean.date = date;
            mListMonthDate.add(bean);
            calendars.add(Calendar.DAY_OF_MONTH, 1);

        }

        if(type==0){
            updateMsg();
        }else{
            updateZbMsg();
        }
    }
}
