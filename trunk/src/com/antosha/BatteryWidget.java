package com.antosha;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryWidget extends AppWidgetProvider {
    private Intent service = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        service = new Intent(context, BatteryService.class);
        context.startService(service);

//        Toast.makeText(context, "onUpdate(1)", Toast.LENGTH_LONG).show();
        //Создаем новый RemoteViews
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

//        remoteViews.setTextViewText(R.id.percent, "" + new Random().nextInt(100));

        //обновляем виджет
//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        context.stopService(service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Intent batteryIntent = context.getApplicationContext().registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int rawlevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        double scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        double level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = rawlevel / scale;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        remoteViews.setTextViewText(R.id.percent, "Level is " + level);
//        remoteViews.setTextViewText(R.id.percent, "" + i++);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);




//        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
//            public void run() {
//                ;//wl.release();
//            }
//        }, 10, TimeUnit.SECONDS);
    }

    //    @Override
    public void onReceive1(Context context, Intent intent) {
        super.onReceive(context, intent);
        Toast.makeText(context, "onReceive(1)", Toast.LENGTH_LONG).show();

//        if (UPDATE_WIDGET.equals(intent.getAction())) {
//            Toast.makeText(context, "FUUUCK", Toast.LENGTH_LONG).show();
//        }


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        Toast.makeText(context, "Level is " + level + "/" + scale, Toast.LENGTH_SHORT).show();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        remoteViews.setTextViewText(R.id.percent, "Level is " + level + "/" + scale);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}