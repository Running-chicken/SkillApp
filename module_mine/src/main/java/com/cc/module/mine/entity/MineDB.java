package com.cc.module.mine.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class MineDB extends LitePalSupport implements Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
