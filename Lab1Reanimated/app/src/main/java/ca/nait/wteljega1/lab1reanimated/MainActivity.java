package ca.nait.wteljega1.lab1reanimated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences prefs;
    View mainView;

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
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    // Menu Creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_preferences:
            {
                Intent intent = new Intent(this,SettingsActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_view_reviews:
            {
                Intent intent = new Intent(this,ReceiveActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String backgroundColor = prefs.getString("preference_main_bg_color", "#702963");
        mainView.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    // onClick method
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_send:
            {
                //string z =
            }
        }
    }

    // Posting to Server method
    private void postToServer(String message)
    {
        String username = prefs.getString("preference_user_name", "JustARandom");

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/Lab01Servlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("DATA", message));
            formParameters.add(new BasicNameValuePair("LOGIN_NAME", username ));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}