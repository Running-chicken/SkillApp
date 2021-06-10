package com.cc.skillapp.activity;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;

public class LocationActivity extends BaseActivity {

    private TextView tvLocationMode;

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


    }
}
