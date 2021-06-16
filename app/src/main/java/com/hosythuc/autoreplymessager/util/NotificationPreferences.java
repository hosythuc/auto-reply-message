package com.hosythuc.autoreplymessager.util;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreferences {
    private static NotificationPreferences notificationPreferences = null;
    private SharedPreferences sharedPreferences = null;

    private NotificationPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("com.hosythuc.autoreplymessager", Context.MODE_PRIVATE);
    }

    public static NotificationPreferences getInstance(Context context) {
        if (notificationPreferences == null) {
            notificationPreferences = new NotificationPreferences(context);
        }
        return notificationPreferences;
    }

    public void putBoolean(String key, Boolean value) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key) {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(key, false);
    }
}
