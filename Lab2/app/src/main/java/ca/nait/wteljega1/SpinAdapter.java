package ca.nait.wteljega1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinAdapter extends ArrayAdapter
{
    // week 4 day 3 lesson applied
    private Context context;
    private ArrayList listTitleArray;

    public SpinAdapter(Context context, int textViewResourceId, ArrayList objects)
    {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.listTitleArray = objects;
    }

    public int getCount()
    {
        return listTitleArray.size();
    }

    public ListTitle getItem(int pos)
    {
        return (ListTitle) listTitleArray.get(pos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView getLabel = new TextView(context);

        getLabel.setTextColor(Color.BLACK);
        getLabel.setText(((ListTitle)(listTitleArray.get(position))).getListDescription());
        return getLabel;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        MainActivity activity = (MainActivity) context;

        LayoutInflater inflater = activity.getLayoutInflater();
        View spinnerRow = inflater.inflate(R.layout.spinner_row, null);

        TextView text_view_Content = (TextView)spinnerRow.findViewById(R.id.text_view_list_title_description);
        text_view_Content.setText(((ListTitle)listTitleArray.get(position)).getListDescription());

        return spinnerRow;
    }
}
