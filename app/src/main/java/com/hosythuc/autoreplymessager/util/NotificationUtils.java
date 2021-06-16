package com.hosythuc.autoreplymessager.util;

import android.app.ActivityManager;
import android.content.Context;

import com.hosythuc.autoreplymessager.service.NotificationListener;

public class NotificationUtils {
    public static final String RUN = "RUN";

    public static boolean isNLServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationListener.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }
}
