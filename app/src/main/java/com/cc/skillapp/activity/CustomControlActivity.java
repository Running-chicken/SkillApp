package com.cc.skillapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.skillapp.BaseActivity;
import com.cc.skillapp.R;
import com.cc.skillapp.view.MyShowViewLayout;

public class CustomControlActivity extends BaseActivity {

    MyShowViewLayout myShowViewLayout;
    private TextView tvFirst,tvTwo,tvThree,tvFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_control);

        myShowViewLayout = findViewById(R.id.msv_test);
        myShowViewLayout.setRightText("我是你大爷");

        tvFirst = findViewById(R.id.tv_clue_item_send_msg);
        tvThree = findViewById(R.id.tv_clue_item_call);
        tvTwo = findViewById(R.id.tv_clue_item_confirm_customer);
        tvFour = findViewById(R.id.tv_four);

        //方法1
        int width = getWindowManager().getDefaultDisplay().getWidth();


        int itemWidth = (width-(dp2px(mContext,64)))/4;

        tvFirst.setLayoutParams(new LinearLayout.LayoutParams(itemWidth,dp2px(mContext,34)));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,0);
        params.width = itemWidth;
        params.height = dp2px(mContext,34);
        params.setMargins(dp2px(mContext,8),0,0,0);

        tvTwo.setLayoutParams(params);
        tvThree.setLayoutParams(params);
        tvFour.setLayoutParams(params);


        //方法2

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int widthTwo = displayMetrics.widthPixels;
        Log.i("cuican",width+"");
        Log.i("cuican",widthTwo+"");


    }


    public static int dp2px(Context context, float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
