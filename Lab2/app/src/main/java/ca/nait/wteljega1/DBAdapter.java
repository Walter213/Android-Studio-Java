package ca.nait.wteljega1;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class DBAdapter extends SimpleCursorAdapter
{
    static final String[] columns = {};
    static final int[] ids = {};

    public DBAdapter(Context context, Cursor cursor)
    {
        super(context, R.layout.archived_row, cursor, null, null);
    }

    // called to draw each row in the cursor
    @Override
    public void bindView(View row, Context context, Cursor cursor)
    {

    }
}
