package com.cc.skillapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cc.library.base.util.DBHelper;

public class PersonContentProvider extends ContentProvider {

    DBHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper  = DBHelper.getInstance(getContext());
        return true;
    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return dbHelper.getReadableDatabase().query("person",null,s,strings1,null,null,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int result = dbHelper.getReadableDatabase().update("person",contentValues,s,strings);
        //关键 修改完后 要通知更新
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}
