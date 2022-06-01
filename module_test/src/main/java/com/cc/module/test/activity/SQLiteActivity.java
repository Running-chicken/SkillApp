package com.cc.module.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.cc.library.base.entity.Person;
import com.cc.library.base.util.DBHelper;
import com.cc.library.base.util.SQLiteUtils;
import com.cc.library.base.util.StringUtils;
import com.cc.library.base.util.Utils;
import com.cc.module.test.R;
import com.cc.module.test.databinding.TestActivitySqliteBinding;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

    TestActivitySqliteBinding mBinding;
    DBHelper dbHelper;
    private String name;
    private int age;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = TestActivitySqliteBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        dbHelper = DBHelper.getInstance(this);


        mBinding.tvInsert.setOnClickListener(view -> {
            if (isNameNull() || isAgeNull()){
                Utils.toast(this,"姓名年龄不能为空");
                return;
            }
            name = mBinding.etInputName.getText().toString();
            age = Integer.parseInt(mBinding.etInputAge.getText().toString());
            Person person = new Person(name, age);
            String result = dbHelper.insert(person) ? "插入成功" : "插入失败";
            mBinding.tvResult.setText("结果是："+result);;
        });

        mBinding.tvDelete.setOnClickListener(view -> {
            if(isIdNull()){
                Utils.toast(this,"id不能为空");
                return;
            }
            id = Integer.parseInt(mBinding.etInputId.getText().toString());
            String result = dbHelper.delete(id) ? "删除成功" : "删除失败";
            mBinding.tvResult.setText("结果是："+result);
        });

        mBinding.tvUpdate.setOnClickListener(view -> {
            if (isNameNull() || isAgeNull() || isIdNull()){
                Utils.toast(this,"姓名年龄id不能为空");
                return;
            }
            id = Integer.parseInt(mBinding.etInputId.getText().toString());
            name = mBinding.etInputName.getText().toString();
            age = Integer.parseInt(mBinding.etInputAge.getText().toString());

            String result = dbHelper.update(id,name,age) ? "修改成功" : "修改失败";
            mBinding.tvResult.setText("结果是："+result);
        });

        mBinding.tvQuery.setOnClickListener(view -> {
            if(isIdNull()){
                Utils.toast(this,"id不能为空");
                return;
            }
            id = Integer.parseInt(mBinding.etInputId.getText().toString());
            List<Person> list = dbHelper.query(id);

            String result = (list!=null && list.size()>0) ? list.get(0).getName() : "无数据";
            mBinding.tvResult.setText("结果是："+result);

        });



    }

    private boolean isNameNull(){
        return StringUtils.isNullOrEmpty(mBinding.etInputName.getText().toString());
    }

    private boolean isAgeNull(){
        return StringUtils.isNullOrEmpty(mBinding.etInputAge.getText().toString());
    }

    private boolean isIdNull(){
        return StringUtils.isNullOrEmpty(mBinding.etInputId.getText().toString());
    }

}
