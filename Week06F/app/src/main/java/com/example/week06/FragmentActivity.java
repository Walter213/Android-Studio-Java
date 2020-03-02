package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button buttonOne = findViewById(R.id.button_one);
        buttonOne.setOnClickListener(this);
        Button buttonTwo = findViewById(R.id.button_two);
        buttonTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_one:
            {
                loadFragment(new FragmentOne());
                break;
            }
            case R.id.button_two:
            {
                loadFragment(new FragmentTwo());
                break;
            }
        }
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }
}
