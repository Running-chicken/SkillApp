package com.cc.skillapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import com.cc.skillapp.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

    public static int getImageId(Context context,String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName,null,context.getPackageName());
    }

    /**
     * 保存json到本地
     * @param mActivity
     * @param filename
     * @param content
     */
    public static File dir = new File(Environment.getExternalStorageDirectory() + "/crmproject/json/");

    public static void saveToSDCard(Activity mActivity, String filename, String content) {
        String en = Environment.getExternalStorageState();
        //获取SDCard状态,如果SDCard插入了手机且为非写保护状态
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            try {
                dir.mkdirs(); //create folders where write files
                File file = new File(dir, filename);

                OutputStream out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                toast(mActivity,"保存成功");
                mActivity.finish();
            } catch (Exception e) {
                e.printStackTrace();
                toast(mActivity,"保存失败");
            }
        } else {
            //提示用户SDCard不存在或者为写保护状态
            toast(mActivity,"SDCard不存在或者为写保护状态");
        }
    }

    /**
     * 从本地读取json
     */
    public static String readTextFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(dir + "/" + filePath);
            InputStream in = null;
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                sb.append((char) tempbyte);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**读取本地文件 带中文*/
    public static String readLocalText(String filePath) {
        String fileContent = "";
        try {
            File file = new File(dir + "/" + filePath);
            //读取的时指定GBK编码格式，若中文出现乱码请尝试utf-8，window默认编码格式为GBK
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                fileContent +=lineTxt;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
