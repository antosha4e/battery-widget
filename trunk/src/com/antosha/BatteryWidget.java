package com.antosha;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

        //lol(context);
    }

    private void lol(Context context) {
        Toast.makeText(context, "is null", Toast.LENGTH_LONG);
        Resources res = context.getApplicationContext().getResources();
        if (res == null) {
            Toast.makeText(context, "res null", Toast.LENGTH_LONG);
            return;
        }
        AssetManager assetManager = res.getAssets();
        if (assetManager == null) {
            Toast.makeText(context, "asset null", Toast.LENGTH_LONG);
            return;
        }
        try {
            InputStream is = assetManager.open("config.properties");
            if (is != null) {
                Properties properties = new Properties();
                properties.load(new BufferedInputStream(is));
                Toast.makeText(context, properties.getProperty("theme", "lol"), Toast.LENGTH_LONG);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "is null", Toast.LENGTH_LONG);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        context.stopService(service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Intent batteryIntent = context.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int rawlevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        double scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        double level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = rawlevel / scale;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        Intent pendingIntent = new Intent(context, BatteryActivity.class);
        PendingIntent pendingintent = PendingIntent.getActivity(context, 0, pendingIntent, 0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setOnClickPendingIntent(R.id.main, pendingintent);

        remoteViews.setTextViewText(R.id.percent, "Level is " + level);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}