package com.cc.skillapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cc.skillapp.utils.DangerousPermissions;
import com.cc.skillapp.utils.PermissionUtils;
import com.example.library_base.util.RouterPath;

public class MainSplashActivity extends BaseActivity {

    private static final String[] LOCATION_STATE_STORAGE =
            { DangerousPermissions.READ_PHONE_STATE,DangerousPermissions.READ_EXTERNAL_STORAGE,DangerousPermissions.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //显示于全面屏
            try {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermisssions();
    }

    /**
     * 处理权限的方法
     */
    public void requestPermisssions(){
        if (!PermissionUtils.checkPermissions(MainSplashActivity.this,LOCATION_STATE_STORAGE)){
            ActivityCompat.requestPermissions(MainSplashActivity.this,LOCATION_STATE_STORAGE, PermissionUtils.REQUEST_SPLASH_PERMISSIONS);
        }else {
            skipToMain();
        }
    }



    /**
     * 这里需要处理授权的结果， 复写onRequestPermissionsResult方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isAllGranted = true;//是否全部权限授权
        for (int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_DENIED){
                isAllGranted = false;
                break;
            }
        }
        if (isAllGranted){//已全部授权
            skipToMain();
        }else {//有未授权的权限
            PermissionUtils.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        }
    }

    private void skipToMain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                finish();
                ARouter.getInstance().build(RouterPath.Main.MAIN_HOST).navigation();
            }
        }).start();

    }
}
