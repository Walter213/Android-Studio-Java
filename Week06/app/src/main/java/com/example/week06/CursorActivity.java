package com.example.week06;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CursorActivity extends BaseActivity
{
    static final String TAG = "CursorActivity";
    DBManager dbMan;
    SQLiteDatabase db;
    Cursor cursor;
    ListView lv;
    DBAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);

        lv = findViewById(R.id.list_view_cursor);
        dbMan = new DBManager(this);
        db = dbMan.getReadableDatabase();
    }

    @Override
    protected void onResume()
    {
        cursor = db.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        adapter = new DBAdapter(this, cursor);
        lv.setAdapter(adapter);

        Log.d(TAG, "in OnResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
