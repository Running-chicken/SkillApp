package com.cc.skillapp.activity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.example.library_base.util.Utils;

public class LocationActivity extends BaseActivity {

    private TextView tvLocationMode;
    private ImageView ivTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_location);

        tvLocationMode = findViewById(R.id.tv_location_mode);

        tvLocationMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

                    Log.i("cuican","定位关闭");
                }else{
                    Log.i("cuican","定位开启");
                }

            }
        });


        ivTest = findViewById(R.id.iv_test_drawable);
        String imgPath = "ic_default_portrait_circle";
        int imageResouce = Utils.getImageId(mContext,imgPath);
        ivTest.setImageResource(imageResouce);

    }
}
