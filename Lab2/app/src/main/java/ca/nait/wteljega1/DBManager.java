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
    static final String Table_Name = "ListTitles";
    static final String LT_LID = BaseColumns._ID;
    static final String LT_Description = "";

    public DBManager(Context context)
    {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //String createSQL
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        //database.execSQL("drop table if exists " + TABLE_NAME);
        //Log.d(TAG, " updating table " + TABLE_NAME);

        // explicitly call the override
        //onCreate(database);
    }
}
