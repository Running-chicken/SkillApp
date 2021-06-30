package com.cc.skillapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.cc.skillapp.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getSelf().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void toast(Context c, String msg) {
        if(null!=msg){
            Toast.makeText(c,msg,Toast.LENGTH_SHORT).show();
        }
    }

    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
