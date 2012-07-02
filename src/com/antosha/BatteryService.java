package com.antosha;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 14.05.12
 */
public class BatteryService extends Service {
    BroadcastReceiver mBI = null;

    @Override
    public void onStart(Intent intent, int startId) {
        if (mBI == null) {
            mBI = new BatteryReceiver();
            IntentFilter mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//            mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(mBI, mIntentFilter);
        }
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}