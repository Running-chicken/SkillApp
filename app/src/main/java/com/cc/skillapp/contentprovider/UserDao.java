package com.cc.skillapp.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cc.skillapp.utils.Utils;

public class UserDao {
    private SQLiteDatabase database;
    public UserDao(SQLiteDatabase database){
        this.database = database;
    }

    public boolean insert(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("age",user.getAge());
        long insertResult = database.insert("user",null,contentValues);
        if(insertResult==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean delete(User user){
        int deleteResult = database.delete("user","user_id=?",new String[]{user.getUserid()+""});
        if(deleteResult==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("age",user.getAge());
        int updateResult = database.update("user",contentValues,"user_id=?",new String[]{user.getUserid()+""});
        return  updateResult==0 ? false : true;
    }



    public void query(String name){
        Cursor cursor = database.query("user",null,"name=?",new String[]{name},null,null,null);
        while (cursor.moveToNext()){
            Utils.log(getClass(),"查询到："+cursor.getString(1));
        }
    }

    public void queryAll(){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Utils.log(getClass(),"查询："+cursor.getString(1));
        }
    }
}
