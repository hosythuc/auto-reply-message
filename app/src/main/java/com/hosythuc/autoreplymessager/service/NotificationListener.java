package com.hosythuc.autoreplymessager.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.Pair;

import com.hosythuc.autoreplymessager.R;
import com.hosythuc.autoreplymessager.util.NotificationPreferences;
import com.hosythuc.autoreplymessager.util.NotificationUtils;
import com.robj.notificationhelperlibrary.models.PendingNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationListener extends NotificationListenerService {
    private static final String TAG = NotificationListener.class.getSimpleName();

    private List<String> packages = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        
        packages.add("com.facebook.orca");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "start");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        NotificationPreferences notificationPreferences = NotificationPreferences.getInstance(getApplicationContext());
        if (!notificationPreferences.getBoolean(NotificationUtils.RUN)) {
            stopSelf();
            return ;
        }
        if (isHandlePackage(sbn.getPackageName())) {
            Pair<RemoteInput, Notification.Action> pair = sbn.getNotification().findRemoteInputActionPair(true);
            //pair.first.getChoices();
          //  Log.d(TAG, "Result key: " + pair.first.getResultKey());
            ArrayList<RemoteInput> actionInput = new ArrayList<>();
            if (pair.first != null) {
                Intent intent = new Intent();
                RemoteInput.Builder builder = new RemoteInput.Builder(pair.first.getResultKey());
                builder.setLabel(pair.first.getLabel());
                builder.setChoices(pair.first.getChoices());
                builder.setAllowFreeFormInput(pair.first.getAllowFreeFormInput());
                builder.addExtras(pair.first.getExtras());
                actionInput.add(builder.build());
                Bundle bundle = new Bundle();
                bundle.putCharSequence(pair.first.getResultKey(), "Xin chào, \nĐây là trợ lý của Thức. Hiện tại Thức đang bận.");
                RemoteInput[] inputs = actionInput.toArray(new RemoteInput[actionInput.size()]);
                RemoteInput.addResultsToIntent(inputs, intent, bundle);
                try {
                    pair.second.actionIntent.send(getApplicationContext(), 0 , intent);
                } catch (PendingIntent.CanceledException e) {
                    Log.d(TAG, "PendingIntent.CanceledException: " + e.toString());
                    e.printStackTrace();
                }
            }

        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
       // stopForeground(STOP_FOREGROUND_REMOVE);
    }

    private boolean isHandlePackage(String packageName) {
        return packages.contains(packageName);
    }

    private void handleSbn(StatusBarNotification sbn) {
    }

    private void sendReply(Context context, String msg) throws PendingIntent.CanceledException {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        ArrayList<RemoteInput> actualInputs = new ArrayList<>();

    }

    private void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel("com.hosythuc.autoreplymessager.notification", "Notification listener", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("Notification channel");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}