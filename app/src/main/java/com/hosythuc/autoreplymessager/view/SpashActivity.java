package com.hosythuc.autoreplymessager.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.hosythuc.autoreplymessager.R;
import com.hosythuc.autoreplymessager.service.NotificationListener;

public class SpashActivity extends AppCompatActivity {

    private TextView mTextView;

    private Button button;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);


        button = findViewById(R.id.get_stated);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivityForResult(intent, 1001);
            }
        });

        group = findViewById(R.id.group_get_started);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNLServiceRunning()) {
            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SpashActivity.this, MainActivity.class));
                }
            }, 500);

        }
        else {
            group.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNLServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationListener.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }
}