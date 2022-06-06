package com.cc.module.test.entity.javatest;

public class TestFinally {

    public static void main(String[] args) {
        try {
            System.out.println("test finally");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("this is finally");
        }
    }
}
