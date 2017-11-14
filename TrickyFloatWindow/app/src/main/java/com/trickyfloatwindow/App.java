package com.trickyfloatwindow;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by guary on 2017/11/14.
 */

public class App extends Application {

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerScreenReceiver();
    }

    private void registerScreenReceiver() {
        BroadcastReceiver screenReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    // When lock screen, clean all floating views before.
                    FloatWindowManager.getInstance().clearAllViewsBefore();
                    // When lock screen, show the popwindow activity.
                    Intent alarmIntent = new Intent(context, PopWindowActivity.class);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(alarmIntent);
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);
    }
}
