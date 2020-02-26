package ca.nait.wteljega1;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener
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
    public void onClick(View v)
    {
        // currentItemIndex is a global variable

        // Update
        // int id = aItems.get(currentItemIndex).getId();

        // Delete

        // Archived
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    // archived, send to server button
}
