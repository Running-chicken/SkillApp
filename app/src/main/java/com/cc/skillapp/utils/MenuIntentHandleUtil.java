package com.cc.skillapp.utils;

import android.app.Activity;

import com.cc.skillapp.entity.MenuListEntity;

public class MenuIntentHandleUtil {
    /**
     * 统一处理菜单跳转的方法
     * @param entity
     */
    public static void handleMenuFunctionIntent(Activity activity, MenuListEntity entity){
        if (entity == null){
            return;
        }




        switch (entity.functionid){

            default:
                Utils.toast(activity,"敬请期待");
        }
    }


}
