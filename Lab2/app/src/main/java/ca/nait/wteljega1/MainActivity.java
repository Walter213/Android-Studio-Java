package ca.nait.wteljega1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener
{
    SharedPreferences prefs;
    View mainView;
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

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mainView = findViewById(R.id.linear_layout_main);
        String backgroundColor = prefs.getString("preference_main_bg_color", "#008577");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));

        prefs.registerOnSharedPreferenceChangeListener(this);

        Log.d(TAG, "In OnCreate()");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_start_service:
            {
                startService(new Intent(this,GetterService.class));
                break;
            }
            case R.id.menu_item_stop_service:
            {
                stopService(new Intent(this,GetterService.class));
                break;
            }
            case R.id.menu_preferences:
            {
                Intent intent = new Intent(this,SettingsActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        Log.d(TAG, "in onCreateOptionsMenu()");
        return true;
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));
    }
}
