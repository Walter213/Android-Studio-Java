package ca.nait.wteljega1;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

public class GetterService extends Service
{
    static final String TAG = "GetterService";
    static final int DELAY = 6000; // 6 seconds

    DBManager dbManager;
    SQLiteDatabase db;

    @Override
    public void onCreate()
    {
        super.onCreate();
        dbManager = new DBManager(this);
        Log.d(TAG, "in OnCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
