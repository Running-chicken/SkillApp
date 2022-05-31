package com.cc.skillapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library_base.util.Utils;

public class MyContentProvider extends ContentProvider {
    OpenHelper openHelper;
    private static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USER=1;
    private static final int USER_ID=2;
    static {
        MATCHER.addURI("com.cc.myprovider","user",USER);
        MATCHER.addURI("com.cc.myprovider","user#",USER_ID);
    }


    @Override
    public boolean onCreate() {
        openHelper = new OpenHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Utils.log(getClass(),"is query");
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.query("user",null,null,null,null,null,null);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)){
            case USER:
                return "vnd.android.cursor.dir/user";
            case USER_ID:
                return "vnd.android.cursor.item/user";
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
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
        return 0;
    }
}
