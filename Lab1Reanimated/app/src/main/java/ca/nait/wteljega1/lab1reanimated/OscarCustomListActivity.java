package ca.nait.wteljega1.lab1reanimated;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

public class OscarCustomListActivity extends ListActivity implements AdapterView.OnItemSelectedListener
{
    ArrayList<HashMap<String,String>> oscar = new ArrayList<HashMap<String,String>>();
    public static String parameter = "film";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oscar_custom_list);

        Spinner categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category_arrays, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(this);

        displayOscarReviews();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        parameter = parent.getItemAtPosition(position).toString().split(" ")[1].toLowerCase();

        if(parameter.contains("picture"))
        {
            parameter = "film";
        }

        displayOscarReviews();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private void displayOscarReviews()
    {
        String[] keys = new String[] {"REVIEWER", "DATE", "CATEGORY", "NOMINEE", "REVIEW"};
        int[] ids = new int[] {R.id.custom_row_reviewer, R.id.custom_row_date,R.id.custom_row_category, R.id.custom_row_nominee, R.id.custom_row_review};

        SimpleAdapter adapter = new SimpleAdapter(this, oscar, R.layout.activity_oscar_custom_list_row, keys, ids);
        populateList();

        this.setListAdapter(adapter);
    }

    private void populateList()
    {
        BufferedReader in = null;
        try
        {
            Toast.makeText(this, "Category: " + parameter, Toast.LENGTH_LONG).show();
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet" + "?CATEGORY=" + parameter));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            // Clear out list to bring in new filter
            oscar.clear();

            while((line = in.readLine()) != null)
            {
                HashMap<String,String> temp = new HashMap<String,String> ();
                temp.put("REVIEWER", line);

                line = in.readLine();
                temp.put("DATE", line);

                line = in.readLine();
                temp.put("CATEGORY", line);

                line = in.readLine();
                temp.put("NOMINEE", line);

                line = in.readLine();
                temp.put("REVIEW", line);

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
