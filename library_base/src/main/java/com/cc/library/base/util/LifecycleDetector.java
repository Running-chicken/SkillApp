package com.cc.library.base.util;

import android.app.Activity;
import android.app.FragmentManager;

import com.cc.library.base.fragment.LifecycleManagerFragment;
import com.cc.library.base.interfaces.LifecycleListener;

/**
 * 生命周期探测
 */
public class LifecycleDetector {

    public static final String FRAGMENT_TAG = "com.cc.skill.glide.manager";


    private static LifecycleDetector mInstance;
    private LifecycleDetector(){}


    public static LifecycleDetector getInstance(){
        if(mInstance==null){
            synchronized (LifecycleDetector.class){
                if(mInstance==null){
                    mInstance = new LifecycleDetector();
                }
            }
        }
        return mInstance;

    }

    public void with(Activity activity, LifecycleListener lifecycleListener){
        FragmentManager fm = activity.getFragmentManager();
        LifecycleManagerFragment current = getRequestManagerFragment(fm);
        current.getLifecycle().addListener(lifecycleListener);
    }


    LifecycleManagerFragment getRequestManagerFragment(FragmentManager fm){
        LifecycleManagerFragment current = (LifecycleManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if(current==null){
            Utils.log("这是空的");
            current = new LifecycleManagerFragment();
            fm.beginTransaction().add(current,FRAGMENT_TAG).commitAllowingStateLoss();

        }
        return current;
    }

}
