package ca.nait.wteljega1;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    String createdDate;
    String content;
    String completedFlag;
    String listTitle;

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


        if(Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

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
                    values.put(DBManager.LI_COMPLETE, 0);
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

                CheckBox editCheckBox = (CheckBox)findViewById(R.id.update_completed_check_box);

                int completed = 0;

                if (editCheckBox.isChecked())
                {
                    completed = 1;
                }

                ContentValues values = new ContentValues();
                String whereClause = DBManager.LI_LID + " = " + (listItemsArray.get(listItemPosition).getListItemsID());
                db = dbManager.getWritableDatabase();

                values.put(DBManager.LI_LIDESC, updateDescription);
                values.put(DBManager.LI_COMPLETE, completed);

                try
                {
                    db.update(DBManager.TABLE_NAME1, values, whereClause, null);
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_LONG).show();
                }
                catch (Exception sql)
                {
                    Toast.makeText(this, "Error: " + sql, Toast.LENGTH_LONG).show();
                }

                refreshingListView();

                break;
            }
            case R.id.delete_list_item:
            {
                EditText editTextDelete = (EditText)findViewById(R.id.update_list_view_edit);
                CheckBox completeDelete = (CheckBox)findViewById(R.id.update_completed_check_box);

                deleteRowInDatabase();

                refreshingListView();

                editTextDelete.setText("");
                completeDelete.setChecked(false);
            }
            case R.id.add_to_archive:
            {
                EditText editTextArchive = (EditText)findViewById(R.id.update_list_view_edit);
                CheckBox completeArchive = (CheckBox)findViewById(R.id.update_completed_check_box);

                // Post To Server Method
                postToServer();

                // Delete From ListView Method
                deleteRowInDatabase();

                Toast.makeText(this, "List Item Successfully Achieved", Toast.LENGTH_LONG).show();

                refreshingListView();

                editTextArchive.setText("");
                completeArchive.setChecked(false);
            }
        }
    }

    // Posting To Server
    private void postToServer()
    {
        String username = prefs.getString("preference_user_name", "username");
        String password = prefs.getString("preference_password", "password");

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab02Post.jsp");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();

            formParameters.add(new BasicNameValuePair("LIST_TITLE", listTitle));
            formParameters.add(new BasicNameValuePair("CONTENT", content));
            formParameters.add(new BasicNameValuePair("COMPLETED_FLAG", completedFlag));
            formParameters.add(new BasicNameValuePair("ALIAS", username));
            formParameters.add(new BasicNameValuePair("PASSWORD", password));
            formParameters.add(new BasicNameValuePair("CREATED_DATE", createdDate));

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    // Deleting From Local Database
    private void deleteRowInDatabase()
    {
        ContentValues values = new ContentValues();
        String whereClause = DBManager.LI_LID + " = " + (listItemsArray.get(listItemPosition).getListItemsID());
        db = dbManager.getWritableDatabase();

        try
        {
            db.delete(DBManager.TABLE_NAME1, whereClause, null);
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_LONG).show();
        }
        catch (Exception sql)
        {
            Toast.makeText(this, "Error: " + sql, Toast.LENGTH_LONG).show();
        }
    }

    // Selected Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ListTitle listTitle = (ListTitle) spinner.getAdapter().getItem(position);
        listPosition = position;
        populatingListView();
    }

    // Part Of Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    // Refreshing List View
    public void refreshingListView()
    {
        populatingListView();
    }

    // Populating List View
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

    // On Item Click in ListView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        listItemPosition = position;

        String list_description = listItemsArray.get(listItemPosition).getListDescription();
        EditText update_item = (EditText)findViewById(R.id.update_list_view_edit);
        update_item.setText(list_description);

        int check_box = listItemsArray.get(listItemPosition).getCompleted();
        CheckBox update_complete = (CheckBox)findViewById(R.id.update_completed_check_box);

        if (check_box == 1)
        {
            update_complete.setChecked(true);
        }
        else
        {
            update_complete.setChecked(false);
        }

        String create_date = listItemsArray.get(listItemPosition).getDate();

        String list_title = listTitleArray.get(listPosition).getListDescription();

        // assigning global variables to make it much easier to enter into server
        content = list_description;
        int completed_flag = check_box;
        completedFlag = String.valueOf(completed_flag);
        createdDate = create_date;
        listTitle = list_title;

        Button updateButton = (Button)findViewById(R.id.update_list_item);
        updateButton.setOnClickListener(this);

        Button deleteButton = (Button)findViewById(R.id.delete_list_item);
        deleteButton.setOnClickListener(this);

        Button archiveButton = (Button)findViewById(R.id.add_to_archive);
        archiveButton.setOnClickListener(this);
    }
}
