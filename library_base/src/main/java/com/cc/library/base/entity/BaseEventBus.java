package com.cc.library.base.entity;

public class BaseEventBus {


    private String type;
    private String msg;

    public BaseEventBus(String type){
        this.type = type;
    }

    public BaseEventBus(String type,String msg){
        this.type = type;
        this.msg = msg;
    };

    public String getType() {
        return type;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
