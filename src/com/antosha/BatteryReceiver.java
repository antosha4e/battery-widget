package com.antosha;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        double scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        double level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = rawlevel / scale * 100;
        }

//        Toast.makeText(context, "Level is " + level + "/" + scale, Toast.LENGTH_SHORT).show();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
//        remoteViews.setTextViewText(R.id.percent, "Level is " + level + "/" + scale);
        remoteViews.setTextViewText(R.id.percent, "" + level);
//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        appWidgetManager.updateAppWidget(new ComponentName(context, BatteryWidget.class), remoteViews);
    }
}