package com.trickyfloatwindow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by guary on 2017/8/8.
 */

public class PopWindowActivity extends Activity {
    private static final String TAG = PopWindowActivity.class.getSimpleName();

    private BroadcastReceiver screenReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerScreenReceiver();
    }

    private void registerScreenReceiver() {
        screenReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    FloatWindowManager.getInstance().showDeniedFloatWindow(null);
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    finish();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
        FloatWindowManager.getInstance().clearAllViewsBefore();
    }
}
