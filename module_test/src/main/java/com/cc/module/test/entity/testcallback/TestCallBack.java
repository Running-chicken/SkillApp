package com.cc.module.test.entity.testcallback;

public class TestCallBack {

    public static void main(String[] args) {
        EngineJob engineJob = new EngineJob();
        engineJob.initEvent();
        DecodeJob decodeJob = new DecodeJob(engineJob);
        engineJob.start(decodeJob);
    }
}
