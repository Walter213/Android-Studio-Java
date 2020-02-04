package ca.nait.wteljega1.lab1reanimated;

import android.app.ListActivity;
import android.os.Bundle;
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

public class OscarCustomListActivity extends ListActivity
{
    ArrayList<HashMap<String,String>> oscar = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oscar_custom_list);

        displayOscarReviews();
    }

    private void displayOscarReviews()
    {
        String[] keys = new String[] {"REVIEWER", "DATE", "NOMINEE"};
        int[] ids = new int[] {R.id.custom_row_reviewer, R.id.custom_row_date, R.id.custom_row_nominee};

        SimpleAdapter adapter = new SimpleAdapter(this, oscar, R.layout.activity_oscar_custom_list_row, keys, ids);
        populateList();

        this.setListAdapter(adapter);
    }

    private void populateList()
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String,String> temp = new HashMap<String,String> ();
                temp.put("SENDER", line);

                line = in.readLine();
                temp.put("MESSAGE", line);

                line = in.readLine();
                temp.put("DATE", line);
                oscar.add(temp);
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
