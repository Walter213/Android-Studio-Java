package ca.nait.wteljega1.sharingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.button_send_data);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // create id for the button
        EditText editText = findViewById(R.id.edit_text_data);
        String data = editText.getText().toString();

        // to show message pop up
        // Toast.makeText(this,"You entered: " + data, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, RecieveActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("PREFIX", "From main: ");
        bundle.putString("DATA", data);

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
