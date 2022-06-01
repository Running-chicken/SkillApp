package com.cc.library.base.util;

import android.util.Log;

public class LogUtils {

    public static final String TAG = "skill_app";
    public static LogUtils mLogUtils;


    public static LogUtils getInstance(){

        if(mLogUtils==null){
            synchronized (LogUtils.class){
                if(mLogUtils==null){
                    mLogUtils = new LogUtils();
                }
            }
        }
        return mLogUtils;
    }


    public void debug(String msg){
        Log.d(TAG,msg);
    }

    public void info(String msg){
        Log.i(TAG,msg);
    }

    public void error(String msg){
        Log.e(TAG,msg);
    }
}
