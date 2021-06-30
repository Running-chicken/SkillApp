package com.cc.skillapp.entity;

import java.io.Serializable;
import java.util.List;

public class AllApplicationEntity implements Serializable {

    public List<AllAppMenu> allAppMenu;
    public List<Submenu> defaultMenu;
    public List<Submenu> myMenu;

    public static class AllAppMenu implements Serializable {
        public String functionid;
        public String reponame;
        public String sort;
        public boolean isCheck;
        public List<Submenu> submenu;
    }

    public static class Submenu implements Serializable {
        public String authcheck;
        public String blacklogo;
        public String drivertype;
        public String functionid;
        public String iscanedit;//是否可编辑 1-可以 0-不可以 ,
        public String logincheck;
        public String logo;
        public String reponame;
        public String sort;
        public String wapurl;
        public boolean isAddOrRemove;//显示加号或者减号按钮
    }

}
