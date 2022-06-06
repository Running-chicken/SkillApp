package com.cc.module.test.entity.reference;

import java.util.Arrays;

public class TestReference {
    public static final int SIZE = 10000;
    private double[] arr= new double[SIZE];
    private String id;

    public TestReference(){

    }

    public TestReference(String id){
        super();
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(id+"被回收了");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
