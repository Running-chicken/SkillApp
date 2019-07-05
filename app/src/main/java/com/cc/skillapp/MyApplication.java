package com.cc.skillapp;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication mApp;
    private List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = (MyApplication) getApplicationContext();
    }

    public void push(Activity activity){
        for (int i=0;i<mActivities.size();i++){
            if(mActivities.get(i)==activity){
                return;
            }
        }
        mActivities.add(activity);
    }

    public void pull(Activity activity){
        for(int i=0;i<mActivities.size();i++){
            if(mActivities.get(i)==activity){
                mActivities.remove(i);
                return;
            }
        }
    }

    public static synchronized MyApplication getSelf(){
        return mApp;
    }

    public void ActivitiesDestroy(){
        for(Activity activity : mActivities){
            activity.finish();
        }
    }


}
