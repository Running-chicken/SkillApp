package com.cc.skillapp.entity;

import java.io.Serializable;

public class MenuListEntity implements Serializable {

    /**
     * authcheck : 0
     * blacklogo : string
     * blackselectedlogo : string
     * drivertype : 0
     * functionid : 0
     * ischeck : string
     * linktype : 0
     * logincheck : 0
     * logo : string
     * reponame : string
     * repotype : 0
     * selectedlogo : string
     * sort : 0
     * wapurl : string
     */

    public String authcheck;
    public String blacklogo;
    public String blackselectedlogo;
    public String drivertype;
    public String functionid;
    public String ischeck;
    public String linktype;
    public String logincheck;
    public String logo;
    public String reponame;
    public String repotype;
    public String selectedlogo;
    public int sort;
    public String wapurl;

    /**页面的class*/
    public transient Class pageClazz;
    /**代办的数量*/
    public transient String todoCount;
}
