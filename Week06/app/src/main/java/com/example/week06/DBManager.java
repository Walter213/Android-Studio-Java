package com.example.week06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper
{
    static final String TAG = "DBManager";
    static final String DB_NAME = "chatter.db";
    static final int DB_VERSION = 1;

    static final String TABLE_NAME = "chatter";

    static final String C_ID = BaseColumns._ID;
    static final String C_DATE = "post_date";
    static final String C_SENDER = "sender";
    static final String C_DATA = "data";

    // default constructor
    public DBManager(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Called when database does not exist
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table " + TABLE_NAME + " (" + C_ID + " int primary key, "
                        + C_DATE + " text, " + C_SENDER + " text, " + C_DATA + " text)";
        Log.d(TAG, "sql = " + sql);
        db.execSQL(sql);
    }

    // Called when version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_NAME);
        Log.d(TAG, "in onUpgrade");
    }
}
