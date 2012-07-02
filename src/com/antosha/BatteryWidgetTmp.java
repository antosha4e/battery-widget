package com.antosha;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryWidgetTmp extends AppWidgetProvider {
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        try {
            context.stopService(new Intent(context, BatteryService.class));//unregisterReceiver(mBI);
        } catch (Exception e) {
            Log.d("BatteryWidget", "Exception on disable: ", e);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        try {
            context.stopService(new Intent(context, BatteryService.class));//if(mBI != null) context.unregisterReceiver(mBI);
        } catch (Exception e) {
            Log.d("BatteryWidget", "Exception on delete: ", e);
        }
    }
}