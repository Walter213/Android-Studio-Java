package ca.nait.gschenk.chatter01;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ReceiveActivity extends AppCompatActivity
{
    private static final String TAG = "RecieveActivity";
    public static final String SENDER = "sender";
    public static final String TEXT = "text";
    public static final String DATE = "myDate";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        getFromServer();
    }

    private void getFromServer()
    {
        String[] keys = new String[]{SENDER, TEXT, DATE};

        BufferedReader in = null;
        TextView textBox = findViewById(R.id.text_view_chatter);

        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/JSONServlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            Log.d(TAG, "got 1 here");

            while((line = in.readLine()) != null)
            {
                textBox.append(line + "\n");
                Log.d(TAG, "***" + line + "***");
            }

            Log.d(TAG, "got 2 here");
            in.close();
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
           String [] names = new String[]{SENDER, TEXT, DATE};

        }

    }
}












