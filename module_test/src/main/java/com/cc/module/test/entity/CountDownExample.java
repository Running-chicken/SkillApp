package com.cc.module.test.entity;

import java.util.concurrent.CountDownLatch;

public class CountDownExample {


    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        Service service = new Service(countDownLatch);

        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    service.exec();
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all thread close");

    }



    public static class Service{
        CountDownLatch latch;

        public Service(CountDownLatch latch){
            this.latch = latch;
        }

        public void exec(){
            try {
                System.out.println(Thread.currentThread().getName()+"执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }


        }


    }
}
