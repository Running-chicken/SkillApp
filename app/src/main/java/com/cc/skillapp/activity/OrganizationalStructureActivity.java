package com.cc.skillapp.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.adapter.OrganizationStructureAdapter;
import com.cc.skillapp.entity.DeptEntity;
import com.cc.skillapp.entity.DeptEntity.DeptItemEntity;

import java.util.ArrayList;
import java.util.List;

public class OrganizationalStructureActivity extends BaseActivity implements OrganizationStructureAdapter.OnItemOperationListener {


    private HorizontalScrollView hsvOrg;

    private OrganizationStructureAdapter orgAdapter;

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

    private TextView tvAddDept;
    private TextView tvDeptSet;
    private TextView tvDept;
    private TextView tvStaff;
    private View vDeptLine;
    private View vStaffLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizational_structure);

        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        tvStaff.setOnClickListener(mOnClick);
        tvDept.setOnClickListener(mOnClick);
        tvDeptSet.setOnClickListener(mOnClick);
        tvAddDept.setOnClickListener(mOnClick);

    }

    View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_org_dept:
                    vDeptLine.setVisibility(View.VISIBLE);
                    vStaffLine.setVisibility(View.GONE);
                    tvDept.setTextColor(Color.parseColor("#297EFF"));
                    tvStaff.setTextColor(Color.parseColor("#83868F"));
                    break;
                case R.id.tv_org_staff:
                    vDeptLine.setVisibility(View.GONE);
                    vStaffLine.setVisibility(View.VISIBLE);
                    tvDept.setTextColor(Color.parseColor("#83868F"));
                    tvStaff.setTextColor(Color.parseColor("#297EFF"));
                    break;
                case R.id.tv_add_dept:
                    break;
                case R.id.tv_dept_set:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        new DeptListTask(selectId).execute();
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


        selectId = "5";
        setHorizontalScrollView(list);

    }

    private void initView() {
        hsvOrg = (HorizontalScrollView)findViewById(R.id.hsv_orginazational);
        rvOrg = (RecyclerView)findViewById(R.id.rv_organizational);
        tvAddDept = (TextView)findViewById(R.id.tv_add_dept);
        tvDeptSet = (TextView)findViewById(R.id.tv_dept_set);
        tvDept = (TextView)findViewById(R.id.tv_org_dept);
        tvStaff = (TextView)findViewById(R.id.tv_org_staff);
        vDeptLine = findViewById(R.id.v_line_dept);
        vStaffLine = findViewById(R.id.v_line_staff);

        orgAdapter = new OrganizationStructureAdapter(mContext, bottomList,this);

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
            View view = LayoutInflater.from(this).inflate(R.layout.item_org_top, null);

            TextView tvName = (TextView) view.findViewById(R.id.tv_org_top_name);
            tvName.setText(list.get(i).deptName);

            ImageView ivArrow = (ImageView)view.findViewById(R.id.iv_org_arrow);
            if(i==(list.size()-1)){
                tvName.setTextColor(Color.parseColor("#83868F"));
                ivArrow.setVisibility(View.GONE);
            }else{
                tvName.setTextColor(Color.parseColor("#297EFF"));
                ivArrow.setVisibility(View.VISIBLE);
            }
            llayout.addView(view);
            tvName.setOnClickListener(new View.OnClickListener() {
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
                rvOrg.setVisibility(View.INVISIBLE);
            }

        }
    }



}
