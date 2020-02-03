package ca.nait.wteljega1.customizedspinner;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;

import org.w3c.dom.Text;

public class MySpinListener implements AdapterView.OnItemSelectedListener
{
    public MainActivity activity;

    public MySpinListener (Context context)
    {
        activity = (MainActivity)context;
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View row, int position, long id)
    {
        Chat chat = (Chat) spinner.getAdapter().getItem(position);

        EditText et_sender = activity.findViewById(R.id.et_sender);
        EditText et_date = activity.findViewById(R.id.et_date);
        EditText et_content = activity.findViewById(R.id.et_content);

        et_sender.setText(chat.getChatSender());
        et_date.setText(chat.getChatDate());
        et_content.setText(chat.getChatContent());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // called when list is empty
    }
}
