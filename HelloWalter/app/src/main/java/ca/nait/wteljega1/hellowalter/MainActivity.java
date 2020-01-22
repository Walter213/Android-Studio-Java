package ca.nait.wteljega1.hellowalter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// AppCompatActivity make sure that it is backwards compatible
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
