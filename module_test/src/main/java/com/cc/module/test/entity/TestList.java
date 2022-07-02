package com.cc.module.test.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestList {


    public static void main(String[] args) {

//        testLinkedList();

        testHashSet();


    }

    //测试ArrayList
    public static void testArrayList(){
        List<String> list = new ArrayList<>();
        list.add("cc");
        list.add("cc");
        list.add("cc");
        list.add("cc");
        list.add("cc");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    //测试fail-fast
    public static void testLinkedList(){
        List<String> list = Collections.synchronizedList(new LinkedList<String>());
        list.add("cuican");
        list.add("fwe");
        list.add("www");

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list){
                    Iterator<String> iterator = list.iterator();
                    while (iterator.hasNext()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(iterator.next());
                    }
                }


            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("zz");
            }
        }).start();

    }

    public static void testHashSet(){
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("cc");
        hashSet.add("cc");
        for(String item : hashSet){
            System.out.println(item);
        }


    }

}
