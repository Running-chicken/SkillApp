package com.cc.library.base.interfaces;


import android.app.Application;

/**
 * 动态配置 Application
 */
public interface ModuleInitImpl {

    /**
     * 初始化优先执行的
     * @param application
     * @return
     */
    boolean onInitAhead(Application application);


    /**
     * 初始化靠后执行的
     * @param application
     * @return
     */
    boolean onInitLow(Application application);


}
