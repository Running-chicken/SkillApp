package com.cc.module.test.entity.tproxy;

public class MyTestApiImpl implements MyTestApi{
    @Override
    public void add(int a, int b) {
        System.out.println(a+b);
    }

    @Override
    public void showName(String name) {
        System.out.println(name);
    }

    @Override
    public void showAge() {

    }
}
