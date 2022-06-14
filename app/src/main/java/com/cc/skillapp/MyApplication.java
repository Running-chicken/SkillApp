package com.cc.skillapp;

import android.app.Activity;
import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cc.library.base.config.ModuleLifecycleConfig;
import com.cc.library.base.netconfig.RetrofitManager;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends LitePalApplication {

    private static MyApplication mApp;
    private List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = (MyApplication) getApplicationContext();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);

        RetrofitManager.getInstance().init(BuildConfig.BASE_HOST,false);

        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        ModuleLifecycleConfig.getInstance().initModuleLow(this);

//        if(LeakCanary.isInAnalyzerProcess(this)){
//            return;
//        }
//        LeakCanary.install(this);


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
