package com.cc.library.base.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cc.library.base.entity.Person;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "skill.db";
    public static final int version = 1;
    public static DBHelper dbHelper;


    public DBHelper(Context context){
        super(context,TABLE_NAME,null,version);
    }

    public static DBHelper getInstance(Context context){
        if(dbHelper==null){
            synchronized (DBHelper.class){
                if(dbHelper==null){
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS person(id INTEGER PRIMARY KEY AUTOINCREMENT,name vachar(32),age INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(Person user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("age",user.getAge());
        long insertResult = dbHelper.getReadableDatabase().insert("person",null,contentValues);
        return insertResult!=-1;
    }

    public boolean delete(int id){
        int deleteResult = dbHelper.getReadableDatabase().delete("person","id=?",new String[]{id+""});
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
        int updateResult = dbHelper.getReadableDatabase().update("person",contentValues,"id=?",new String[]{id+""});
        return  updateResult==0 ? false : true;
    }


    public List<Person> query(int id){
        Cursor cursor = dbHelper.getReadableDatabase().query("person",null,"id=?",new String[]{id+""},null,null,null);
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
        Cursor cursor = dbHelper.getReadableDatabase().query("person",null,null,null,null,null,null);
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
