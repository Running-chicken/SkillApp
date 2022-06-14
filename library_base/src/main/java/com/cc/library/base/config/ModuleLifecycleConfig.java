package com.cc.library.base.config;

import android.app.Application;

import com.cc.library.base.interfaces.ModuleInitImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModuleLifecycleConfig {



    private ModuleLifecycleConfig(){}

    private static class SingletonHolder{
        public static ModuleLifecycleConfig instance = new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance(){
        return SingletonHolder.instance;
    }

    public void initModuleAhead(Application application){
        for(String moduleName : ModuleList.initModuleNames){
            try {
                Class clazz = Class.forName(moduleName);
                Object object = clazz.newInstance();
                Method method = clazz.getDeclaredMethod("onInitAhead",Application.class);
                method.invoke(object,application);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }



















    public void initModuleLow(Application application){
        for(String moduleName : ModuleList.initModuleNames){
            try {
                Class clazz = Class.forName(moduleName);
                ModuleInitImpl moduleInit = (ModuleInitImpl) clazz.newInstance();
                moduleInit.onInitLow(application);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }


        }
    }

}
