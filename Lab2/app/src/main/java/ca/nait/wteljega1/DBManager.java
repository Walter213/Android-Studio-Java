package ca.nait.wteljega1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    static final String LI_COMPLETE = "List_Completed_Flag";

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
        String ListItemsTable = "create table " + TABLE_NAME1 + " (" + LI_LID + " integer primary key autoincrement, " + LI_LILTD + " integer, "
                + LI_LIDESC + " text, " + LI_DATE + " text, " + LI_COMPLETE + " int)";
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

//    public List<String> getListTitleDescription()
//    {
//        List<String> desc = new ArrayList<String>();
//
//        //String query = "SELECT " + LT_DESCRIPTION + " FROM " + TABLE_NAME;
//        String query = "SELECT * FROM " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst())
//        {
//            do {
//                // desc.add(cursor.getString(cursor.getColumnIndex(LT_LID)));
//                desc.add(cursor.getString(cursor.getColumnIndex(LT_DESCRIPTION)));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return desc;
//    }

//    public List<String> getListItems()
//    {
//        List<String> getListItems = new ArrayList<String>();
//
//        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + LI_LID + " = " + getListTitleDescription();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst())
//        {
//            do {
//                getListItems.add(cursor.getString(cursor.getColumnIndex(LI_LID)));
//                getListItems.add(cursor.getString(cursor.getColumnIndex(LI_LILTD)));
//                getListItems.add(cursor.getString(cursor.getColumnIndex(LI_LIDESC)));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return  getListItems;
//    }
}
