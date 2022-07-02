package com.cc.library.base.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifecycleObserver implements LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener(){
        Utils.log(getClass(),"ON RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disConnectListener(){
        Utils.log(getClass(),"ON PAUSE");
    }
}
