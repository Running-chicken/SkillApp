package com.cc.module.test.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cc.library.base.util.Utils;
import com.cc.module.test.databinding.TestActivitySaveBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestActivitySaveBinding mBinding = TestActivitySaveBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        SharedPreferences sharedPreferences  = getSharedPreferences("skillSp",MODE_PRIVATE);
        Utils.log(getClass(),sharedPreferences.getString("code","default"));

        mBinding.tvSp.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("code","20220606");
            editor.commit();
        });

        mBinding.tvFile.setOnClickListener(view -> {
            saveFile();
        });

        mBinding.tvShowFile.setOnClickListener(view -> {
            testFileInputStream();
        });

        getSaveFile();

    }

    private void getSaveFile(){
        try {
            FileInputStream fileInputStream = this.openFileInput("skill.txt");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[3];
            int length = 0;
            while ((length = fileInputStream.read(buffer))!=-1){
                byteArrayOutputStream.write(buffer,0,length);
                Utils.log(getClass(),new String(byteArrayOutputStream.toByteArray()));
            }
            byteArrayOutputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void testFileInputStream(){

        try {
            FileInputStream fileInputStream = this.openFileInput("skill.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);

            Utils.log(getClass(),new String(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void saveFile(){
        try {
            FileOutputStream fos = this.openFileOutput("skill.txt", Context.MODE_PRIVATE);
            String saveStr = "this is file txt";
            fos.write(saveStr.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}