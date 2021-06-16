package com.hosythuc.autoreplymessager.model;

import android.app.Notification;
import android.service.notification.StatusBarNotification;

public class NotificationEntry {
    private Notification notification;

    private String packageName;

    public NotificationEntry(StatusBarNotification sbn) {
        notification = sbn.getNotification();
        packageName = sbn.getPackageName();
    }

    public String getTitle() {
        if (notification != null) {
            return notification.extras.getString("android.title").toString();
        }
        return "";
    }

    public String getContent() {
        if (notification != null) {
            return notification.extras.getString("android.text").toString();
        }
        return "";
    }

    public String getPackageName() {
        return packageName;
    }
}
