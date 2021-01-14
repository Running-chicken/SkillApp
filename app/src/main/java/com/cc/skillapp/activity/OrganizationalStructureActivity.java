package com.cc.skillapp.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.OrganizationAdapter;
import com.cc.skillapp.entity.DeptEntity;
import com.cc.skillapp.entity.DeptEntity.DeptItemEntity;

import java.util.ArrayList;
import java.util.List;

public class OrganizationalStructureActivity extends BaseActivity implements OrganizationAdapter.OnItemOperationListener {


    private HorizontalScrollView hsvOrg;

    private OrganizationAdapter orgAdapter;

    private RecyclerView rvOrg;
    private LinearLayoutManager mLayoutManager;

    /**顶部展示List*/
    private List<DeptItemEntity> topList = new ArrayList<>();
    /**底部展示List*/
    private List<DeptItemEntity> bottomList = new ArrayList<>();

    /**测试用*/
    private List<DeptItemEntity> cityList = new ArrayList<>();
    private List<DeptItemEntity> districtList = new ArrayList<>();
    private List<DeptItemEntity> centerList = new ArrayList<>();
    private List<DeptItemEntity> typeList = new ArrayList<>();

    /**当前展示列表的部门id*/
    private String selectId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizational_structure);

        initView();
        initData();
    }

    private void initData() {
        List<DeptItemEntity> list = new ArrayList<>();
        list.add(new DeptItemEntity("中国房天下","1"));
        list.add(new DeptItemEntity("北京搜房科技发展有限公司","2"));
        list.add(new DeptItemEntity("郭公庄房天下大厦","3"));
        list.add(new DeptItemEntity("产品技术中心","4"));
        list.add(new DeptItemEntity("Android技术开发部","5"));

        cityList.add(new DeptItemEntity("北京搜房科技发展有限公司","2"));
        cityList.add(new DeptItemEntity("上海搜房科技发展有限公司","100"));
        cityList.add(new DeptItemEntity("广州搜房科技发展有限公司","100"));

        districtList.add(new DeptItemEntity("郭公庄房天下大厦","3"));
        districtList.add(new DeptItemEntity("潘家园房天下大厦","100"));
        districtList.add(new DeptItemEntity("科怡路房天下大厦","100"));

        centerList.add(new DeptItemEntity("产品技术中心","4"));
        centerList.add(new DeptItemEntity("平台","100"));
        centerList.add(new DeptItemEntity("销售","100"));

        typeList.add(new DeptItemEntity("Android技术开发部","5"));
        typeList.add(new DeptItemEntity("iOS技术开发部","100"));
        typeList.add(new DeptItemEntity("pc技术开发部","100"));



        setHorizontalScrollView(list);
    }

    private void initView() {
        hsvOrg = (HorizontalScrollView)findViewById(R.id.hsv_orginazational);

        rvOrg = (RecyclerView)findViewById(R.id.rv_organizational);


        orgAdapter = new OrganizationAdapter(mContext, bottomList,this);

        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvOrg.setLayoutManager(mLayoutManager);
        rvOrg.setAdapter(orgAdapter);

    }




    private void setHorizontalScrollView(final List<DeptItemEntity> list){
        //初始化一个当前循环的List
        final List<DeptItemEntity> currentList = new ArrayList<>();
        currentList.addAll(list);

        hsvOrg.removeAllViews();
        LinearLayout llayout = new LinearLayout(this);
        llayout.setOrientation(LinearLayout.HORIZONTAL);
        llayout.setGravity(Gravity.CENTER_VERTICAL);
        llayout.setFocusableInTouchMode(true);
        for (int i = 0; i < list.size(); i++) {
            final int selectNum=i;
            View view = LayoutInflater.from(this).inflate(R.layout.item_organizational, null);

            TextView tv_branch_name = (TextView) view.findViewById(R.id.tv_branch_name);
            tv_branch_name.setText(list.get(i).deptName);
            if(i==(list.size()-1)){
                tv_branch_name.setTextColor(Color.parseColor("#394043"));
            }else{
                tv_branch_name.setTextColor(Color.parseColor("#008aff"));
            }
            llayout.addView(view);
            tv_branch_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hsvOrg.removeAllViews();
                    topList.clear();
                    for (int j=0;j<selectNum+1;j++){
                        topList.add(currentList.get(j));
                    }
                    setHorizontalScrollView(topList);


                    new DeptListTask(currentList.get(selectNum).deptid).execute();

                }
            });
        }
        hsvOrg.addView(llayout);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                hsvOrg.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });

    }

    @Override
    public void onItemClick(DeptEntity.DeptItemEntity entity, int position) {

        rvOrg.setFocusable(false);
        rvOrg.setEnabled(false);
        topList.add(bottomList.get(position));
        setHorizontalScrollView(topList);
        selectId= bottomList.get(position).deptid;
        new DeptListTask(selectId).execute();

    }


    //获取部门列表
    class DeptListTask extends AsyncTask<Void, Void, List<DeptItemEntity>> {

        String deptid;
        public DeptListTask(String deptid){
            this.deptid = deptid;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected List<DeptItemEntity> doInBackground(Void... params) {
            if("1".equals(deptid)){
                return cityList;
            }else if("2".equals(deptid)){
                return districtList;
            }else if("3".equals(deptid)){
                return centerList;
            }else if("4".equals(deptid)){
                return typeList;
            }

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DeptItemEntity> depListEntity) {
            super.onPostExecute(depListEntity);

            if(depListEntity!=null){
                rvOrg.setVisibility(View.VISIBLE);
                rvOrg.setFocusable(true);
                rvOrg.setEnabled(true);


                bottomList.clear();
                bottomList.addAll(depListEntity);
                //这里写入adapter
                orgAdapter.notifyDataSetChanged();

            }else{
                rvOrg.setVisibility(View.GONE);
            }

        }
    }



}
