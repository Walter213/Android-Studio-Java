package ca.nait.wteljega1;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener
{
    SharedPreferences prefs;
    View mainView;
    DBManager dbManager;
    SQLiteDatabase db;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database Creation
        dbManager = new DBManager(this);
        db = dbManager.getWritableDatabase();
        Log.d(TAG, "Database Created");

        // Loading Preferences and background able to change
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mainView = findViewById(R.id.linear_layout_main);
        String backgroundColor = prefs.getString("preference_main_bg_color", "#008577");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));

        prefs.registerOnSharedPreferenceChangeListener(this);

        // Save To Spinner Button
        Button createNewListItem = (Button)findViewById(R.id.save_to_spinner);
        createNewListItem.setOnClickListener(this);

        // Populating Spinner
        // Start here next class which is tomorrow

        Log.d(TAG, "In OnCreate()");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.save_to_spinner:
            {
                EditText newListItem = (EditText)findViewById(R.id.edit_text_add_new_spinner_item);
                String newSpinnerEntry = newListItem.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DBManager.LT_DESCRIPTION, newSpinnerEntry);
                long newRowID = db.insert(DBManager.TABLE_NAME, null, values);

                newListItem.setText("");
                Log.d(TAG, DBManager.LT_DESCRIPTION + " " + newRowID + " is inserted into database");
                break;
            }
        }
    }
}
