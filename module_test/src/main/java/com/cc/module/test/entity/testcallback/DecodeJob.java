package com.cc.module.test.entity.testcallback;

import android.os.SystemClock;

public class DecodeJob {


    public interface CallBackListener {
        public void doSomething();
    }

    EngineJob engineJob;
    public DecodeJob(EngineJob engineJob){
        this.engineJob = engineJob;
    }


    public void runEvent(){
        System.out.println("Decode do something");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Decode finish");
        engineJob.doSomething();
    }
}
