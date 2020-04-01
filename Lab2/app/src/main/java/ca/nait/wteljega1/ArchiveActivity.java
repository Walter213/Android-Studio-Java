package ca.nait.wteljega1;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchiveActivity extends ListActivity
{
    SharedPreferences prefs;

    ArrayList<HashMap<String,String>> archiveItems = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_list_view);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        displayArchivedItems();
    }

    private void displayArchivedItems()
    {
        String[] keys = new String[] {"Posted Date", "List Title", "Content", "Completed Flag"};
        int[] ids = new int[] {R.id.custom_row_date, R.id.custom_row_title, R.id.custom_row_content, R.id.custom_row_complete};

        SimpleAdapter adapter = new SimpleAdapter(this, archiveItems, R.layout.archive_list_view_row, keys, ids);
        populateArchive();

        this.setListAdapter(adapter);
    }

    private void populateArchive()
    {
        String username = prefs.getString("preference_user_name", "username");
        String password = prefs.getString("preference_password", "password");

        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab02Get.jsp?ALIAS=" + username + "&PASSWORD=" + password));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String,String> temp = new HashMap<String,String> ();
                temp.put("Posted Date", line);

                line = in.readLine();
                temp.put("List Title", line);

                line = in.readLine();
                temp.put("Content", line);

                line = in.readLine();
                temp.put("Completed Flag", line);

                archiveItems.add(temp);
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
