package com.cc.skillapp.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.cc.skillapp.R;
import com.cc.skillapp.databinding.ActivityContentProviderBinding;

import java.util.Random;

public class ContentProviderActivity extends AppCompatActivity {

    OpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContentProviderBinding mBinding = DataBindingUtil.setContentView(this,R.layout.activity_content_provider);
        openHelper = new OpenHelper(this);
        SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
        UserDao userDao = new UserDao(sqLiteDatabase);



        mBinding.tvAdd.setOnClickListener(view -> {
            initData();
        });

        mBinding.tvQueryAll.setOnClickListener(view -> {
            userDao.queryAll();
        });



    }

    private void initData() {
        SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
        UserDao userDao = new UserDao(sqLiteDatabase);
        sqLiteDatabase.beginTransaction();
        try {

            for(int i=0;i<10;i++){
                User user = new User();
                user.setName("jay chou"+i);
                Random random = new Random();
                user.setAge(random.nextInt(30));
                userDao.insert(user);
            }
            sqLiteDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }

    }
}