package com.cc.skillapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivitySlideMenuBinding;

public class SlideMenuActivity extends AppCompatActivity {


    private String[] contentItems = { "Content Item 1", "Content Item 2", "Content Item 3",
            "Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7",
            "Content Item 8", "Content Item 9", "Content Item 10", "Content Item 11",
            "Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15",
            "Content Item 16","Content Item 1", "Content Item 2", "Content Item 3",
            "Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7",
            "Content Item 8", "Content Item 9", "Content Item 10", "Content Item 11",
            "Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15",
            "Content Item 16" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySlideMenuBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_slide_menu);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contentItems);
        mBinding.lvMenu.setAdapter(mAdapter);
        mBinding.slRoot.setScrollEvent(mBinding.lvMenu);
        mBinding.tvMenu.setOnClickListener(view -> {
            if(mBinding.slRoot.isLeftLayoutVisible()){
                mBinding.slRoot.scrollToRightLayout();
            }else{
                mBinding.slRoot.scrollToLeftLayout();
            }
        });



    }
}