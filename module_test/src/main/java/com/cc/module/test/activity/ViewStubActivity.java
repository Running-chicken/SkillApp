package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.cc.module.test.R;
import com.cc.module.test.databinding.TestMeasureBinding;

public class ViewStubActivity extends AppCompatActivity {

    View inflateView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_mesure_spec);

        TestMeasureBinding mBinding = DataBindingUtil.setContentView(this,R.layout.test_activity_mesure_spec);

        mBinding.viewTest.setOnClickListener(view -> {
            if(inflateView == null){
                ViewStub stub = findViewById(R.id.view_stub_test);
                inflateView = stub.inflate();
                textView = inflateView.findViewById(R.id.tv_vp_item);
                textView.setText("this is viewStub");
            }

        });
    }
}