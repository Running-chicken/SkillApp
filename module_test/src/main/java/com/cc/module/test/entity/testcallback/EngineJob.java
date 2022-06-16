package com.cc.module.test.entity.testcallback;

public class EngineJob implements DecodeJob.CallBackListener {

    public void initEvent(){
        System.out.println("EngineJob 初始化");
    }

    @Override
    public void doSomething() {
        System.out.println("EngineJob 处理");
    }

    public void start(DecodeJob decodeJob){
        decodeJob.runEvent();
    }
}
