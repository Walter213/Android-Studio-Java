package ca.nait.wteljega1;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_home:
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d(TAG, "Going To Home Page");
                break;
            }
            case R.id.menu_archive:
            {
                Intent intent = new Intent(this, ArchiveActivity.class);
                startActivity(intent);
                Log.d(TAG, "Going To Archived Items");
                break;
            }
            case R.id.menu_item_preferences:
            {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                Log.d(TAG, "Going To Preferences");
                break;
            }
        }
        return true;
    }
}
