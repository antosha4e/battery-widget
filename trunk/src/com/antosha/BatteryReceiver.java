package com.antosha;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.widget.RemoteViews;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryReceiver extends BroadcastReceiver {
    private int i = 0;
    private double _level = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        if (pm.isScreenOn()) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            double scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            double level = -1;
            if (rawlevel >= 0 && scale > 0) {
                level = rawlevel / scale * 100;
            }

        if(_level == level) {
            return;
        }
        _level = level;

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setTextViewText(R.id.percent, ++i + " : " + level);
            appWidgetManager.updateAppWidget(new ComponentName(context, BatteryWidget.class), remoteViews);
//        }
    }
}