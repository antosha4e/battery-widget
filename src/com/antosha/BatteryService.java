package com.antosha;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryService extends Service {
    BroadcastReceiver mBI = null;

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_LONG).show();
        if (mBI == null) {
            mBI = new BatteryReceiver();
            IntentFilter mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(mBI, mIntentFilter);
        }

        // Build the widget update for today
//        RemoteViews updateViews = buildUpdate(this, intent);
//        if (updateViews != null) {
//            try {
//                // Push update for this widget to the home screen
//                ComponentName thisWidget = new ComponentName(this, BatteryWidget.class);
//                if (thisWidget != null) {
//                    AppWidgetManager manager = AppWidgetManager.getInstance(this);
//                    if (manager != null && updateViews != null) {
//                        manager.updateAppWidget(thisWidget, updateViews);
//                    }
//                }
//
//                //stop the service, clear up memory, can't do this, need the Broadcast Receiver running
////                stopSelf();
//            } catch (Exception e) {
//                Log.e("Widget", "Update Service Failed to Start", e);
//            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mBI != null) {
                unregisterReceiver(mBI);
            }
        } catch (Exception e) {
            Log.e("Widget", "Failed to unregister", e);
        }
    }

    /**
     * Build a widget update to show the current Wiktionary
     * "Word of the day." Will block until the online API returns.
     * @param context
     * @param intent
     * @return
     */
    public RemoteViews buildUpdate(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//
//        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        Toast.makeText(context, "Level is " + level + "/" + scale, Toast.LENGTH_SHORT).show();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        remoteViews.setTextViewText(R.id.percent, "Level is " + level + "/" + scale);

//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);


        return remoteViews;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}