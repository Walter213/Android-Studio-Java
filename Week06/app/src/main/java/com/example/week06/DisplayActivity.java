package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity
{
    static final String TAG = "DisplayActivity";
    DBManager dbManager;
    SQLiteDatabase db;
    Cursor cursor;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        textView = findViewById(R.id.text_view_display_chatter);

        dbManager = new DBManager(this);
        db = dbManager.getReadableDatabase();

        Log.d(TAG, "in onCreate()");
    }

    @Override
    protected void onResume()
    {
        cursor = db.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        String sender, date, data, output;
        while(cursor.moveToNext())
        {
            sender = cursor.getString(cursor.getColumnIndex(DBManager.C_SENDER));
            date = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
            data = cursor.getString(cursor.getColumnIndex(DBManager.C_DATA));
            output = String.format("%s: from %s - %s\n", date, sender, data);

            textView.append(output);
        }
        Log.d(TAG, "in onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        db.close();
        Log.d(TAG, "in onDestroy()");
        super.onDestroy();
    }
}
