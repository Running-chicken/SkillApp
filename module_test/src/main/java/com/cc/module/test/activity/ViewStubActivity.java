package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cc.library.base.util.Utils;
import com.cc.module.test.R;
import com.cc.module.test.databinding.TestMeasureBinding;
import com.cc.module.test.entity.TestParcel;

public class ViewStubActivity extends AppCompatActivity {

    View inflateView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_mesure_spec);
        TestParcel parcel = getIntent().getParcelableExtra("data");
        Utils.log(parcel.getName());

        TestMeasureBinding mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_mesure_spec);

        mBinding.viewTest.setOnClickListener(view -> {
            if(inflateView == null){
                ViewStub stub = findViewById(R.id.view_stub_test);
                inflateView = stub.inflate();
                textView = inflateView.findViewById(R.id.tv_vp_item);
                textView.setText("this is viewStub");
            }

        });

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            if(!Settings.canDrawOverlays(this)){
                Utils.log(getClass(),"未打开");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent,100);


            }else{
                handleAdd();
            }
        }




    }

    private void handleAdd() {
        Window window = getWindow();

        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.x = 100;
        layoutParams.y = 100;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Button textView = new Button(this);
        textView.setText("添加tv到window");
        windowManager.addView(textView,layoutParams);
    }
}