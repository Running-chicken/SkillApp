package com.cc.library.base.entity;

public class BaseEventBus {


    private String type;

    public BaseEventBus(String type){
        this.type = type;
    };

    public String getType() {
        return type;
    }

}
