package ca.nait.wteljega1;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener
{
    SharedPreferences prefs;
    View mainView;
    Spinner spinner;
    DBManager dbManager;
    SQLiteDatabase db;

    public static int spinnerItemNumber;

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

        // start from https://stackoverflow.com/questions/54801724/how-i-get-selected-spinner-value-id-from-sqlite-database
        // Populating Spinner
        spinner = (Spinner)findViewById(R.id.list_of_items);
//        loadSpinnerData();
//        spinner.setOnClickListener(this);

        Log.d(TAG, "In OnCreate()");
    }

    // Preferences
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    // Loading Spinner Data
//    public void loadSpinnerData()
//    {
//        DBManager dataBase = new DBManager(getApplicationContext());
//
//        List<String> getDesc = dataBase.getListTitleDescription();
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getDesc);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(spinnerAdapter);
//    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.save_to_spinner:
            {
                // inserting list title into database
                EditText newListTitle = (EditText)findViewById(R.id.edit_text_add_new_spinner_item);
                String newSpinnerEntry = newListTitle.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DBManager.LT_DESCRIPTION, newSpinnerEntry);
                long newRowID = db.insert(DBManager.TABLE_NAME, null, values);

                // clearing edit text
                newListTitle.setText("");

                // reloading spinner data
                loadSpinnerData();

                Log.d(TAG, DBManager.LT_DESCRIPTION + " " + newRowID + " is inserted into database");
                break;
            }
            case R.id.add_to_list:
            {
                EditText newListItem = (EditText)findViewById(R.id.edit_text_add_to_list_view);
                String newListViewEntry = newListItem.getText().toString();

                ContentValues values = new ContentValues();
                // need to grab the id from the spinner
                // myspinner.getSelectedItemId();
                //values.put(DBManager.LI_LILTD, somehow have to get the spinner id from that);
                values.put(DBManager.LI_LIDESC, newListViewEntry);

                // clearing edit text
                newListItem.setText("");

                break;
            }
        }
    }
}
