package ca.nait.wteljega1.lab1reanimated;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ReceiveActivity extends ListActivity
{
    //private static final String REVIEW = "review";
    //private static final String REVIEWER = "reviewer";
    //private static final String NOMINEE = "nominee";
    //private static final String CATEGORY = "";
    //private static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recieve_review);
        getFromServer();
    }

    private void getFromServer()
    {
        BufferedReader in = null;
        ArrayList oscarreview = new ArrayList();

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/showReviews.jsp"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            String NL = System.getProperty("line.separator");

            while((line = in.readLine()) != null)
            {
                oscarreview.add(line);
            }
            in.close();

            ArrayAdapter<ArrayList> adapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, oscarreview);
            setListAdapter(adapter);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
