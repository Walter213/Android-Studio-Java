package ca.nait.wteljega1;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class CursorAdapter extends SimpleCursorAdapter
{
    // Week 6 Day 1

    static final String[] FROMDATABASE = {DBManager.LI_LILTD, DBManager.LI_LID, DBManager.LI_LIDESC, DBManager.LI_DATE, DBManager.LI_COMPLETE};
    static final int[] TOLISTVIEW = {R.id.list_title_id, R.id.list_id, R.id.list_description, R.id.list_date, R.id.list_completed_flag};

    public CursorAdapter(Context context, Cursor c)
    {
        super(context, R.layout.list_view_row, c, FROMDATABASE, TOLISTVIEW);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        super.bindView(view, context, cursor);
    }
}
