package com.cc.module.test.entity;

public class TestNew implements Runnable {
    public static void main(String[] args) {

        //泛型声明
        Class clazz = Test.class;
        System.out.println(clazz.getTypeParameters().length);

        //实现接口
        Class tn = TestNew.class;
        System.out.println(tn.getInterfaces().length);
    }

    @Override
    public void run() {

    }

    public static class Test<A,B,C>{
        public String name;
    }




}
