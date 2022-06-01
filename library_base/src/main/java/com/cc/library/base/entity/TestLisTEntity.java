package com.cc.library.base.entity;

import java.util.List;

public class TestLisTEntity {


    public String msg;
    public boolean succeed;
    public List<MenuIcon> data;
    public String code;
    public String requestId;

    public static class MenuIcon {
        public String permiId;
        public String permiName;
        public String permiDesc;
        public String permiCode;
        public String permiIcon;
        public String hots;
        public String jumpType;
        public String jumpPage;
    }
}
