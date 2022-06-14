package com.cc.module.test.entity;

public class ThreadLocalDemo {

    private static ThreadLocal<String> localVar = new ThreadLocal<>();
    static void print(String str){
        System.out.println(str+" "+localVar.get());
    }

    public static void main(String[] args) {
        localVar.set("this is mainThread");
        System.out.println(localVar.get());


        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set("is localA Thread");
                System.out.println(localVar.get());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.remove();
                System.out.println(localVar.get());
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set("is localB Thread");
                System.out.println(localVar.get());
            }
        }).start();

    }
}
