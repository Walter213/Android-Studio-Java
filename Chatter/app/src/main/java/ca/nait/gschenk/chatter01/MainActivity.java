package ca.nait.gschenk.chatter01;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences prefs;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //prefs = this.getSharedPreferences(this,);

        if(Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy ourPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(ourPolicy);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mainView = findViewById(R.id.linear_layout_main);
        String bgColor = prefs.getString("preference_main_bg_color", "#009999");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        Button sendButton = this.findViewById(R.id.button_send);
        Button viewButton = this.findViewById(R.id.button_view);

        sendButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);

        // this allows use to start changing preferences
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

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
            case R.id.menu_item_view_text_view:
            {
                Intent intent = new Intent(this,ReceiveActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_view_system_list:
            {
                Intent intent = new Intent(this,SystemListActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_settings:
            {
                Intent intent = new Intent(this,SettingsActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_send:
            {
                String x = ReceiveActivity.SENDER;

                EditText textBox = findViewById(R.id.edit_text_message);
                String chatter = textBox.getText().toString();
                postToServer(chatter);
                textBox.setText("");
                break;
            }
            case R.id.button_view:
            {
                Intent intent = new Intent(this, ReceiveActivity.class);
                startActivity(intent);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void postToServer(String message)
    {
        String userName = prefs.getString("preference_user_name", "unknown");
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost form = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> formParameters = new ArrayList<NameValuePair>();
            formParameters.add(new BasicNameValuePair("DATA", message));
            formParameters.add(new BasicNameValuePair("LOGIN_NAME", userName ));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParameters);
            form.setEntity(formEntity);
            client.execute(form);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColor = prefs.getString("preference_main_bg_color", "#009999");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}







