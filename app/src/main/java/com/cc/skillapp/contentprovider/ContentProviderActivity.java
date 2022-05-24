package com.cc.skillapp.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

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



        mBinding.tvInit.setOnClickListener(view -> {
            initData();
        });

        mBinding.tvQueryAll.setOnClickListener(view -> {
            userDao.queryAll();
        });

        mBinding.tvDelete.setOnClickListener(view -> {
            boolean result = userDao.delete(1);
            Toast.makeText(this,result?"成功":"失败",Toast.LENGTH_LONG).show();
        });

        mBinding.tvUpdate.setOnClickListener(view -> {
            User user = new User();
            user.setUserid(2);
            user.setName("cuican");
            user.setAge(29);
            boolean result = userDao.update(user);
            Toast.makeText(this,result?"成功":"失败",Toast.LENGTH_LONG).show();
        });

        mBinding.tvAdd.setOnClickListener(view -> {
            User user = new User();
            user.setName("zhenzhen");
            user.setAge(25);
            boolean result = userDao.insert(user);
            Toast.makeText(this,result?"成功":"失败",Toast.LENGTH_LONG).show();
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