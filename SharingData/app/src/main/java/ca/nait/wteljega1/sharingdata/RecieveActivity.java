package ca.nait.wteljega1.sharingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecieveActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);

        Bundle bundle = getIntent().getExtras();
        String prefix = bundle.getString("PREFIX");
        String data = bundle.getString("DATA");

        TextView textView = findViewById(R.id.text_view_recieved);
        textView.setText(prefix + data);
    }
}
