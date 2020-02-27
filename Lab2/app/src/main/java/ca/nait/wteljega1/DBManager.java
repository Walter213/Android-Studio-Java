package ca.nait.wteljega1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper
{
    static final String TAG = "DBManager";
    static final String DB_Name = "Lab2.db";
    static final int DB_Version = 1;
    static final String TABLE_NAME = "ListTitles";
    static final String LT_LID = BaseColumns._ID;
    static final String LT_DESCRIPTION = "";

    public DBManager(Context context)
    {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        // List Titles Creation
        String createSQLListTitles = "create table " + TABLE_NAME + " (" + LT_LID + " int primary key, "
                + LT_DESCRIPTION + " text)";

        database.execSQL(createSQLListTitles);

        // List Items Creation
//        String createSQLListItems = "create table " + TABLE_NAME + " (" + LT_LID + " int primary key, "
//                + LT_DESCRIPTION + " text)";
//
//        database.execSQL(createSQLListTitles);

        Log.d(TAG, "in OnResume()");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TABLE_NAME);
    }
}
