package ca.nait.wteljega1;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener
{
    DBManager dbManager;
    SQLiteDatabase db;

    SharedPreferences prefs;
    View SpinnerColor, ListViewBg;

    Spinner spinner;
    SpinnerAdapter adapter;

    ArrayList<ListTitle> listTitleArray;
    ArrayList<ListItems> listItemsArray;

    int listPosition;
    int listItemPosition;

    ListView listview;
    Cursor cursor;

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
        SpinnerColor = findViewById(R.id.list_of_items);
        ListViewBg = findViewById(R.id.items_list_view);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Save To Spinner Button (List Titles Table)
        Button createNewListItem = (Button)findViewById(R.id.save_to_spinner);
        createNewListItem.setOnClickListener(this);

        // Populating Spinner
        loadSpinnerData();

        adapter = new SpinAdapter(this, android.R.layout.simple_spinner_item, listTitleArray);

        spinner = (Spinner) findViewById(R.id.list_of_items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // populating list view
        listview = (ListView)findViewById(R.id.items_list_view);
        listview.setOnItemClickListener(this);

        // Adding To List Items Table
        Button createNewListEntry = (Button)findViewById(R.id.add_to_list);
        createNewListEntry.setOnClickListener(this);

        Log.d(TAG, "In OnCreate()");
    }

    // Preferences
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        SpinnerColor.setBackgroundColor(Color.parseColor(backgroundColor));
        ListViewBg.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    // Refreshing Spinner Data
    public void refreshSpinnerData()
    {
        db = dbManager.getWritableDatabase();
        loadSpinnerData();
        adapter = new SpinAdapter(this, android.R.layout.simple_spinner_item, listTitleArray);
        spinner = (Spinner) findViewById(R.id.list_of_items);
        spinner.setAdapter(adapter);
    }

    // Loading Spinner Data
    public void loadSpinnerData()
    {
        listTitleArray = new ArrayList<ListTitle>();

        db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.TABLE_NAME, null, null, null, null, null, null);
        startManagingCursor(cursor);

        String listNameDescription;
        int listID;

        while (cursor.moveToNext())
        {
            listID = cursor.getInt(cursor.getColumnIndex(DBManager.LT_LID));
            listNameDescription = cursor.getString(cursor.getColumnIndex(DBManager.LT_DESCRIPTION));
            ListTitle listName = new ListTitle(listID, listNameDescription);

            listTitleArray.add(listName);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.save_to_spinner:
            {
                EditText checkEditTextAdd = (EditText) findViewById(R.id.edit_text_add_new_spinner_item);
                if(checkEditTextAdd.getText().toString().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Enter List Name", Toast.LENGTH_LONG).show();
                }
                else
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
                    refreshSpinnerData();

                    Log.d(TAG, DBManager.LT_DESCRIPTION + " " + newRowID + " is inserted into database");
                }

                break;
            }
            case R.id.add_to_list:
            {
                EditText checkEditTextAdd = (EditText) findViewById(R.id.edit_text_add_to_list_view);
                if(checkEditTextAdd.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Enter List Item Description", Toast.LENGTH_LONG).show();
                }
                else
                {
                    EditText newListItem = (EditText)findViewById(R.id.edit_text_add_to_list_view);
                    String newListViewEntry = newListItem.getText().toString();

                    ContentValues values = new ContentValues();

                    values.put(DBManager.LI_LILTD, ((ListTitle) listTitleArray.get(listPosition)).getListTitleID());
                    values.put(DBManager.LI_LIDESC, newListViewEntry);
                    values.put(DBManager.LI_DATE, DateFormat.getDateTimeInstance().format(new Date()));
                    long newRowID = db.insert(DBManager.TABLE_NAME1, null, values);

                    // clearing edit text
                    newListItem.setText("");

                    // refresh list view
                    refreshingListView();
                }

                break;
            }
            case R.id.update_list_item:
            {
                EditText editTextUpdate = (EditText)findViewById(R.id.update_list_view_edit);
                String updateDescription = editTextUpdate.getText().toString();

                ContentValues values = new ContentValues();
                String whereClause = DBManager.LI_LID + " = " + (listItemsArray.get(listItemPosition).getListItemsID());
                db = dbManager.getWritableDatabase();

                values.put(DBManager.LI_LIDESC, updateDescription);

                try
                {
                    db.update(DBManager.TABLE_NAME1, values, whereClause, null);
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_LONG).show();
                }
                catch (Exception error)
                {
                    Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
                }

                refreshingListView();

                break;
            }
            case R.id.delete_list_item:
            {
                EditText editTextDelete = (EditText)findViewById(R.id.update_list_view_edit);

                ContentValues values = new ContentValues();
                String whereClause = DBManager.LI_LID + " = " + (listItemsArray.get(listItemPosition).getListItemsID());
                db = dbManager.getWritableDatabase();

                try
                {
                    db.delete(DBManager.TABLE_NAME1, whereClause, null);
                    Toast.makeText(this, "Delete Successful", Toast.LENGTH_LONG).show();
                }
                catch(Exception error)
                {
                    Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
                }

                refreshingListView();

                editTextDelete.setText("");
            }
            case R.id.add_to_archive:
            {

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ListTitle listTitle = (ListTitle) spinner.getAdapter().getItem(position);
        listPosition = position;
        populatingListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void refreshingListView()
    {
        populatingListView();
    }

    private void populatingListView()
    {
        listItemsArray = new ArrayList<ListItems>();

        db = dbManager.getReadableDatabase();
        String whereClause = DBManager.LI_LILTD + "=" + (listTitleArray.get(listPosition).getListTitleID());

        cursor = db.query(DBManager.TABLE_NAME1, null, whereClause, null, null, null, null);


        int id, listTitleID, complete;
        String desc, date;

        while(cursor.moveToNext())
        {
            id = cursor.getInt(cursor.getColumnIndex(DBManager.LI_LID));
            listTitleID = cursor.getInt(cursor.getColumnIndex(DBManager.LI_LILTD));
            desc = cursor.getString(cursor.getColumnIndex(DBManager.LI_LIDESC));
            date = cursor.getString(cursor.getColumnIndex(DBManager.LI_DATE));
            complete = cursor.getInt(cursor.getColumnIndex(DBManager.LI_COMPLETE));

            ListItems items = new ListItems(id, listTitleID, desc, date, complete);
            listItemsArray.add(items);
        }

        CursorAdapter cursorAdapter = new CursorAdapter(this, cursor);
        listview.setAdapter(cursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        listItemPosition = position;

        String list_description = listItemsArray.get(listItemPosition).getListDescription();
        EditText update_item = (EditText)findViewById(R.id.update_list_view_edit);
        update_item.setText(list_description);

        Button updateButton = (Button)findViewById(R.id.update_list_item);
        updateButton.setOnClickListener(this);

        Button deleteButton = (Button)findViewById(R.id.delete_list_item);
        deleteButton.setOnClickListener(this);

        Button archiveButton = (Button)findViewById(R.id.add_to_archive);
        archiveButton.setOnClickListener(this);
    }
}
