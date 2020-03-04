package ca.nait.wteljega1.simplewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class SimpleWidgetProvider extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName component = new ComponentName(context, SimpleWidgetProvider.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        appWidgetManager.updateAppWidget(component, views);
    }
}
