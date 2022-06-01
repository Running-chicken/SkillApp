package com.cc.library.base.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cc.library.base.entity.Person;

import java.util.ArrayList;
import java.util.List;


public class SQLiteUtils {

    private SQLiteDatabase database;

    public SQLiteUtils(Context context){
        this.database = DBHelper.getInstance(context).getReadableDatabase();
    }



    public boolean insert(Person user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("age",user.getAge());
        long insertResult = database.insert("person",null,contentValues);
        return insertResult!=-1;
    }

    public boolean delete(int id){
        int deleteResult = database.delete("person","id=?",new String[]{id+""});
        return  deleteResult!=0;
    }


    public boolean update(int id,String name,int age){
        ContentValues contentValues = new ContentValues();
        if(!StringUtils.isNullOrEmpty(name)){
            contentValues.put("name",name);
        }
        if(age!=0){
            contentValues.put("age",age);
        }
        int updateResult = database.update("person",contentValues,"id=?",new String[]{id+""});
        return  updateResult==0 ? false : true;
    }


    public List<Person> query(int id){
        Cursor cursor = database.query("person",null,"id=?",new String[]{id+""},null,null,null);
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setId(cursor.getInt(0));
            person.setName(cursor.getString(1));
            person.setAge(cursor.getInt(2));
            persons.add(person);
        }
        return persons;
    }

    public List<Person> queryAll(){
        Cursor cursor = database.query("person",null,null,null,null,null,null);
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setId(cursor.getInt(0));
            person.setName(cursor.getString(1));
            person.setAge(cursor.getInt(2));
            persons.add(person);
        }
        return persons;
    }
}
