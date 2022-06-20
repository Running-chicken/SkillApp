package com.cc.library.base.util;

import android.content.Context;

public class AppContext {

    private AppContext(){};

    private static AppContext instance;

    public static AppContext getInstance(){
        if(instance == null){
            synchronized (AppContext.class){
                if(instance == null){
                    instance = new AppContext();
                }
            }
        }
        return instance;
    }

    private static Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }
}
