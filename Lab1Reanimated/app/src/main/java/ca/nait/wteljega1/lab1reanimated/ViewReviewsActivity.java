package ca.nait.wteljega1.lab1reanimated;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewReviewsActivity extends ListActivity
{
    ArrayList<HashMap<String,String>> viewoscar = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        displayOscarReviews();
    }

    // look at custom list activity on moodle step 5 to continue on, also got to do postOnServer and OnClick Method

    private void displayOscarReviews()
    {
        populateList();
    }

    private void populateList()
    {

    }
}
