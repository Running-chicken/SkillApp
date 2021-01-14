package com.cc.skillapp.entity;

import java.util.List;

public class DeptEntity {

    public List<DeptItemEntity> data;



    public static class DeptItemEntity{
        public String deptName;
        public String deptid;

        public DeptItemEntity(String deptName,String deptid){
            this.deptName = deptName;
            this.deptid = deptid;
        }

    }

}
