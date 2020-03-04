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
    static final String LT_DESCRIPTION = "list_title_desc";

    static final String TABLE_NAME1 = "ListItems";
    static final String LI_LID = BaseColumns._ID;
    static final String LI_LILTD = "list_items_ID";
    static final String LI_LIDESC = "list_description";
    static final String LI_DATE = "list_date";
    static final String LI_COMPLETED = "completed_flag";

    public DBManager(Context context)
    {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        // List Titles Creation
        String ListTitlesTable = "create table " + TABLE_NAME + " (" + LT_LID + " integer primary key autoincrement, "
                + LT_DESCRIPTION + " text)";

        database.execSQL(ListTitlesTable);

        Log.d(TAG, ListTitlesTable);

        // List Items Creation
        String ListItemsTable = "create table " + TABLE_NAME1 + " (" + LI_LID + " integer primary key autoincrement, " + LI_LILTD + " text,"
                + LI_LIDESC + " text," + LI_DATE + " text," + LI_COMPLETED + " text)";

        database.execSQL(ListItemsTable);

        Log.d(TAG, ListItemsTable);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TABLE_NAME);
        Log.d(TAG, " updating table " + TABLE_NAME);

        database.execSQL("drop table if exists " + TABLE_NAME1);
        Log.d(TAG, " updating table " + TABLE_NAME1);

        onCreate(database);
    }
}
